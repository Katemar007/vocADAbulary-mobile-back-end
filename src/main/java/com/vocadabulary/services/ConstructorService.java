package com.vocadabulary.services;

import com.vocadabulary.dto.ConstructorSentenceDTO;
import com.vocadabulary.models.ConstructorSentence;
import com.vocadabulary.models.ConstructorSentenceBlank;
import com.vocadabulary.repositories.ConstructorSentenceRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConstructorService {

    private final ConstructorSentenceRepository repo;

    public ConstructorService(ConstructorSentenceRepository repo) {
        this.repo = repo;
    }

    public ConstructorSentenceDTO getRandomSentence(Long topicId) {
        ConstructorSentence sentence = repo.findRandomByTopicId(topicId)
                .orElseThrow(() -> new RuntimeException("No sentence found for this topic"));

        ConstructorSentenceDTO dto = new ConstructorSentenceDTO();
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

    public boolean validateAnswers(Long sentenceId, List<String> answers, ConstructorSentence sentence) {
        List<String> correct = sentence.getBlanks().stream()
                .sorted(Comparator.comparingInt(ConstructorSentenceBlank::getOrderIndex))
                .map(blank -> blank.getFlashcard().getWord().toLowerCase().trim())
                .collect(Collectors.toList());

        for (int i = 0; i < correct.size(); i++) {
            String userAnswer = answers.get(i).toLowerCase().trim();
            if (!correct.get(i).equals(userAnswer)) {
                return false;
            }
        }
        return true;
    }
}
