package com.vocadabulary.controllers;

import com.vocadabulary.models.UserQuizAttempt;
import com.vocadabulary.services.UserQuizAttemptService;
import com.vocadabulary.auth.MockUserContext;
import com.vocadabulary.dto.UserQuizAttemptDTO; // Import your DTO!
import com.vocadabulary.requests.QuizAttemptRequest; // Import your request class
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz-attempts")
public class UserQuizAttemptController {

    private final UserQuizAttemptService userQuizAttemptService;

    public UserQuizAttemptController(UserQuizAttemptService userQuizAttemptService) {
        this.userQuizAttemptService = userQuizAttemptService;
    }

    @PostMapping
    public UserQuizAttemptDTO createQuizAttempt(@RequestBody QuizAttemptRequest request) {
        Long userId = MockUserContext.getCurrentUser().getId();
        UserQuizAttempt attempt = userQuizAttemptService.createQuizAttempt(userId, request.getQuizId(), request.isPassed());
        return new UserQuizAttemptDTO(attempt);
    }
}