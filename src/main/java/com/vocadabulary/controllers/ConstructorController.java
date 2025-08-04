package com.vocadabulary.controllers;

import com.vocadabulary.dto.ConstructorSentenceDTO;
import com.vocadabulary.models.ConstructorSentence;
import com.vocadabulary.repositories.ConstructorSentenceRepository;
import com.vocadabulary.services.ConstructorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/constructor")
public class ConstructorController {

    private final ConstructorService service;
    private final ConstructorSentenceRepository repo;

    public ConstructorController(ConstructorService service, ConstructorSentenceRepository repo) {
        this.service = service;
        this.repo = repo;
    }

    @GetMapping("/{topicId}/random")
    public ConstructorSentenceDTO getRandom(@PathVariable Long topicId) {
        return service.getRandomSentence(topicId);
    }

    @PostMapping("/validate")
    public Map<String, Object> validate(@RequestBody Map<String, Object> payload) {
        Long sentenceId = ((Number) payload.get("sentenceId")).longValue();
        List<String> answers = (List<String>) payload.get("answers");
        ConstructorSentence sentence = repo.findById(sentenceId)
                .orElseThrow(() -> new RuntimeException("Sentence not found"));

        boolean correct = service.validateAnswers(sentenceId, answers, sentence);

        return Map.of("correct", correct);
    }
}
