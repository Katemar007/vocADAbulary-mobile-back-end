package com.vocadabulary.controllers;

import com.vocadabulary.models.Quiz;
import com.vocadabulary.services.QuizService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    // Get all quizzes
    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }

    // Get quizzes by topic ID
    @GetMapping("/topic/{topicId}")
    public List<Quiz> getQuizzesByTopic(@PathVariable Long topicId) {
        return quizService.getQuizzesByTopic(topicId);
    }
}