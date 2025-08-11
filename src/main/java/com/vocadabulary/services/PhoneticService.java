package com.vocadabulary.services;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PhoneticService {

        private static final Logger log = LoggerFactory.getLogger(PhoneticService.class);

    @Value("${openai.api.key:}")
    private String apiKey;

    private static final String OPENAI_CHAT_URL = "https://api.openai.com/v1/chat/completions";

    private final RestTemplate restTemplate = new RestTemplate();

    public String generateIPA(String word) {
        if (apiKey == null || apiKey.trim().isEmpty()) {
            log.warn("OpenAI API key is missing. Returning empty phonetic for word '{}'", word);
            return ""; // Fallback to empty string
        }

        String prompt = "Give only the IPA phonetic transcription (US English) for the word: " + word +
                ". Do not add explanations or text, just the transcription.";

        Map<String, Object> body = Map.of(
                "model", "gpt-4o-mini",
                "messages", List.of(
                        Map.of("role", "system", "content", "You are a phonetics expert."),
                        Map.of("role", "user", "content", prompt)
                ),
                "max_tokens", 50,
                "temperature", 0
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        try {
            Map<?, ?> response = restTemplate.postForObject(OPENAI_CHAT_URL, request, Map.class);

            // Extract response: choices[0].message.content
            List<Map<String, Object>> choices = (List<Map<String, Object>>) response.get("choices");
            Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
            return message.get("content").toString().trim();
        } catch (RestClientException e) {
            log.error("Failed to generate IPA for word '{}': {}", word, e.getMessage());
            return ""; // Fallback to empty string
        }
    }   
}