package com.vocadabulary.requests;  // or dto

import com.fasterxml.jackson.annotation.JsonProperty;

public class QuizAttemptRequest {
    private Long quizId;

    @JsonProperty("is_passed")
    private boolean isPassed;

    public Long getQuizId() { return quizId; }
    public void setQuizId(Long quizId) { this.quizId = quizId; }
    public boolean isPassed() { return isPassed; }
    public void setPassed(boolean passed) { isPassed = passed; }
}