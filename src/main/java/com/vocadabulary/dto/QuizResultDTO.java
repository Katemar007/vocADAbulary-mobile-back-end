package com.vocadabulary.dto;

import java.time.LocalDateTime;

public class QuizResultDTO {
    private Long id;
    private Long quizId;
    private Long userId;
    private int totalQuestions;
    private int correctAnswers;
    private boolean passed;
    private LocalDateTime takenAt;

    public QuizResultDTO(Long id, Long quizId, Long userId,
                         int totalQuestions, int correctAnswers,
                         boolean passed, LocalDateTime takenAt) {
        this.id = id;
        this.quizId = quizId;
        this.userId = userId;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
        this.passed = passed;
        this.takenAt = takenAt;
    }

    public Long getId() { return id; }
    public Long getQuizId() { return quizId; }
    public Long getUserId() { return userId; }
    public int getTotalQuestions() { return totalQuestions; }
    public int getCorrectAnswers() { return correctAnswers; }
    public boolean isPassed() { return passed; }
    public LocalDateTime getTakenAt() { return takenAt; }
}