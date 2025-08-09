package com.vocadabulary.services;

import com.vocadabulary.models.SentenceTemplate;
import com.vocadabulary.models.SentenceTemplateBlank;
import com.vocadabulary.repositories.FlashcardRepository;
import com.vocadabulary.repositories.SentenceTemplateBlankRepository;
import com.vocadabulary.repositories.SentenceTemplateRepository;
import org.springframework.stereotype.Service;
import com.vocadabulary.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class SentenceGenerationService {

    private final FlashcardRepository flashcardRepo;
    private final TopicRepository topicRepo;
    private final SentenceTemplateRepository templateRepo;
    private final SentenceTemplateBlankRepository blankRepo;

    @Value("${OPENAI_API_KEY}")
    private String apiKey;

    private static final String OPENAI_CHAT_URL = "https://api.openai.com/v1/chat/completions";

    public SentenceGenerationService(FlashcardRepository flashcardRepo,
                                     TopicRepository topicRepo,
                                     SentenceTemplateRepository templateRepo,
                                     SentenceTemplateBlankRepository blankRepo) {
        this.flashcardRepo = flashcardRepo;
        this.topicRepo = topicRepo;
        this.templateRepo = templateRepo;
        this.blankRepo = blankRepo;
    }

    /**
     * Generates a sentence with a blank for a learned word and saves it to the database.
     * This method is idempotent: it will not generate a new sentence if one already exists for the flashcard.
     *
     * @param flashcardId The ID of the flashcard for which to generate the sentence.
     */
    @Async
    public void generateAndSaveSentenceForLearnedWord(Long flashcardId) {
        // ✅ Idempotency: skip if we already generated a template for this flashcard
        if (blankRepo.existsByTargetFlashcardId(flashcardId)) {
            System.out.println("ℹ️ Template already exists for flashcard " + flashcardId + ", skipping.");
            return;
        }

        // 1) Get word and topic
        String word = flashcardRepo.findWordById(flashcardId);
        Long topicId = topicRepo.findTopicIdForFlashcard(flashcardId);
        String topicName = (topicId != null) ? topicRepo.findTopicName(topicId) : "general";

        if (word == null || word.isBlank()) {
            System.out.println("⚠️ No word found for flashcard ID " + flashcardId);
            return;
        }

        // 2) Generate sentence with AI
        String sentence = generateSentenceWithBlank(word, topicName);
        if (sentence == null || !sentence.contains("___")) {
            System.out.println("⚠️ AI did not return a valid sentence for word: " + word);
            return;
        }

        // 3) Save into sentence_templates
        SentenceTemplate tpl = new SentenceTemplate();
        tpl.setTemplateText(sentence);
        tpl.setSource("ai");
        tpl.setContextTopic(topicName);
        templateRepo.save(tpl);

        // 4) Save into sentence_template_blanks
        SentenceTemplateBlank blank = new SentenceTemplateBlank();
        blank.setTemplateId(tpl.getId());
        blank.setBlankIndex(0);
        blank.setTargetFlashcardId(flashcardId);
        blankRepo.save(blank);

        System.out.println("✅ Saved AI sentence for word '" + word + "': " + sentence);
    }

    // Generates a sentence with a blank using OpenAI API
    private String generateSentenceWithBlank(String word, String topic) {
        String prompt = String.format(
            "Write ONE simple English sentence about the topic \"%s\" that contains the word \"%s\". " +
            "Replace that word with exactly three underscores ___ once. " +
            "No quotes, no explanations, only the sentence.",
            topic, word
        );

        Map<String, Object> body = Map.of(
            "model", "gpt-4.1",
            "messages", List.of(
                Map.of("role", "system", "content", "You are a helpful assistant that generates simple sentences for students studying Software Development being non native english speakers."),
                Map.of("role", "user", "content", prompt)
            ),
            "max_tokens", 50,
            "temperature", 0.7
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        Map<?, ?> response = restTemplate.postForObject(OPENAI_CHAT_URL, request, Map.class);
        if (response != null && response.containsKey("choices")) {
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
            if (!choices.isEmpty()) {
                Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                return message.get("content").toString().trim();
            }
        }
        return null;
    }
}
