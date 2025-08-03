package com.vocadabulary.controllers;

import com.vocadabulary.models.UserQuizAttempt;
import com.vocadabulary.services.UserQuizAttemptService;
import com.vocadabulary.auth.MockUserContext;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz-attempts")
public class UserQuizAttemptController {

    private final UserQuizAttemptService userQuizAttemptService;
    private final MockUserContext mockUserContext;

    public UserQuizAttemptController(UserQuizAttemptService userQuizAttemptService,
                                     MockUserContext mockUserContext) {
        this.userQuizAttemptService = userQuizAttemptService;
        this.mockUserContext = mockUserContext;
    }

    @PostMapping
    public UserQuizAttempt createQuizAttempt(@RequestBody QuizAttemptRequest request) {
        Long userId = MockUserContext.getCurrentUser().getId(); // ✅ Get mock user from header
        return userQuizAttemptService.createQuizAttempt(userId, request.getQuizId(), request.isPassed());
    }

    // DTO for request body
    public static class QuizAttemptRequest {
        private Long quizId;
        private boolean isPassed;

        public Long getQuizId() {
            return quizId;
        }
        public void setQuizId(Long quizId) {
            this.quizId = quizId;
        }
        public boolean isPassed() {
            return isPassed;
        }
        public void setPassed(boolean passed) {
            isPassed = passed;
        }
    }
}