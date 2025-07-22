package com.vocadabulary.controllers;

import com.vocadabulary.models.Flashcard;
import com.vocadabulary.models.Topic;
import com.vocadabulary.services.FlashcardService;
import com.vocadabulary.services.TopicService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {

    private final TopicService topicService;
    private final FlashcardService flashcardService;

    public TopicController(TopicService topicService, FlashcardService flashcardService) {
        this.topicService = topicService;
        this.flashcardService = flashcardService;
    }

    @GetMapping
    public List<Topic> getAllTopics() {
        return topicService.getAllTopics();
    }

    @GetMapping("/{id}")
    public Topic getTopicById(@PathVariable Long id) {
        return topicService.getTopicById(id);
    }

    @GetMapping("/{id}/flashcards")
    public List<Flashcard> getFlashcardsByTopicId(@PathVariable("id") Long topicId) {
        return flashcardService.getFlashcardsByTopicId(topicId);
    }
}