package com.vocadabulary.controller;

import com.vocadabulary.model.Topic;
import com.vocadabulary.model.Flashcard;
import com.vocadabulary.repository.TopicRepository;
import com.vocadabulary.repository.FlashcardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private FlashcardRepository flashcardRepository;

    @GetMapping
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }

    @GetMapping("/{id}/flashcards")
    public ResponseEntity<List<Flashcard>> getFlashcardsByTopicId(@PathVariable Long id) {
        Optional<Topic> optionalTopic = topicRepository.findById(id);
        if (optionalTopic.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<Flashcard> flashcards = flashcardRepository.findByTopicId(id);
        return ResponseEntity.ok(flashcards);
    }

    @PostMapping
    public ResponseEntity<Topic> createTopic(@RequestBody Topic newTopic) {
        Topic savedTopic = topicRepository.save(newTopic);
        return ResponseEntity.ok(savedTopic);
    }
}