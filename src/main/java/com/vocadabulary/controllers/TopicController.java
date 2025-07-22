package com.vocadabulary.controllers;

import com.vocadabulary.models.Topic;
import com.vocadabulary.repositories.TopicRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topics")
public class TopicController {

    private final TopicRepository topicRepository;

    // Constructor injection for the repository
    public TopicController(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    // GET /api/topics - Returns a list of all topics
    @GetMapping
    public List<Topic> getAllTopics() {
        return topicRepository.findAll();
    }
}