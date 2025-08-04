package com.vocadabulary.controllers;

import com.vocadabulary.dto.QuizDetailDTO;
import com.vocadabulary.services.QuizService;
import com.vocadabulary.auth.MockUserContext;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    // GET /api/quizzes — returns quizzes for the current user, with isHidden
    @GetMapping
    public List<QuizDetailDTO> getAllQuizzesForUser() {
        Long userId = MockUserContext.getCurrentUser().getId();
        return quizService.getAllQuizzesForUser(userId);
    }
}