package com.vocadabulary.controller;

import com.vocadabulary.model.Topic;
import com.vocadabulary.model.Flashcard;
import com.vocadabulary.repository.TopicRepository;
import com.vocadabulary.repository.FlashcardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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
    newTopic.setCreatedAt(LocalDateTime.now());  // ⬅️ add this
    Topic savedTopic = topicRepository.save(newTopic);
    return ResponseEntity.ok(savedTopic);
}

    @PostMapping("/{id}/flashcards")
    public ResponseEntity<Flashcard> createFlashcardUnderTopic(
        @PathVariable Long id,
        @RequestBody Flashcard flashcardRequest) {

    Optional<Topic> optionalTopic = topicRepository.findById(id);
    if (optionalTopic.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    Topic topic = optionalTopic.get();
    flashcardRequest.setTopic(topic); // associate topic with flashcard
    flashcardRequest.setCreatedAt(LocalDateTime.now());

    Flashcard savedFlashcard = flashcardRepository.save(flashcardRequest);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedFlashcard);
}
}