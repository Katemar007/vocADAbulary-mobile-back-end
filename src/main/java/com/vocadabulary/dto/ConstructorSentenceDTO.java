package com.vocadabulary.dto;
import com.vocadabulary.models.ConstructorSentence;
import com.vocadabulary.models.ConstructorSentenceBlank; // Removed or comment out if not available
import com.vocadabulary.repositories.ConstructorSentenceRepository;
import java.util.List;

// Import the ConstructorSentence class (adjust the package as needed)


import java.util.Comparator;
import java.util.stream.Collectors;

public class ConstructorSentenceDTO {
    // Add fields and inner classes as needed, e.g.:
    public Long topicId;
    public Long sentenceId;
    public String sentenceTemplate;
    public List<BlankDTO> blanks;

    public static class BlankDTO {
        public int index;
        public Long flashcardId;
    }

    // Add dependencies as fields or inject as needed
    private ConstructorSentenceRepository repo;

    public ConstructorSentenceDTO getRandomSentence(Long topicId) {
        ConstructorSentence sentence = repo.findRandomByTopicId(topicId)
            .orElseThrow(() -> new RuntimeException("No sentence found for this topic"));

        System.out.println("🧠 Picked sentence ID: " + sentence.getId());

        ConstructorSentenceDTO dto = new ConstructorSentenceDTO();
        dto.topicId = sentence.getTopic().getId();
        dto.sentenceId = sentence.getId();
        dto.sentenceTemplate = sentence.getSentenceTemplate();
        dto.blanks = sentence.getBlanks().stream()
            .sorted(Comparator.comparingInt(ConstructorSentenceBlank::getOrderIndex))
            .map(blank -> {
                ConstructorSentenceDTO.BlankDTO b = new ConstructorSentenceDTO.BlankDTO();
                b.index = blank.getOrderIndex();
                b.flashcardId = blank.getFlashcard().getId();
                return b;
            }).collect(Collectors.toList());

        return dto;
    }
}