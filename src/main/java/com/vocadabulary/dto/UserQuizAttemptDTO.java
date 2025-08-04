package com.vocadabulary.dto;

import com.vocadabulary.models.UserQuizAttempt;


public class UserQuizAttemptDTO {
    private Long id;
    private Long userId;
    private Long quizId;
    private boolean isPassed;

    public UserQuizAttemptDTO(UserQuizAttempt attempt) {
        this.id = attempt.getId();
        this.userId = attempt.getUser().getId();
        this.quizId = attempt.getQuiz().getId();
        this.isPassed = attempt.isPassed();
    }

    // getters (setters optional for serialization, but not strictly needed for responses)
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public Long getQuizId() { return quizId; }
    public boolean isPassed() { return isPassed; }
}
