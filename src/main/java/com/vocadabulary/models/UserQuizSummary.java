package com.vocadabulary.models;

import jakarta.persistence.*;

@Entity
@Table(name = "user_quiz_summary")
public class UserQuizSummary {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "quizzes_attempted", nullable = false)
    private int quizzesAttempted = 0;

    @Column(name = "quizzes_passed", nullable = false)
    private int quizzesPassed = 0;

    @Column(name = "quiz_success_rate", nullable = false)
    private float quizSuccessRate = 0;

    // === Getters & Setters ===
    public Long getUserId() {
        return userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getQuizzesAttempted() {
        return quizzesAttempted;
    }

    public void setQuizzesAttempted(int quizzesAttempted) {
        this.quizzesAttempted = quizzesAttempted;
    }

    public int getQuizzesPassed() {
        return quizzesPassed;
    }

    public void setQuizzesPassed(int quizzesPassed) {
        this.quizzesPassed = quizzesPassed;
    }

    public float getQuizSuccessRate() {
        return quizSuccessRate;
    }

    public void setQuizSuccessRate(float quizSuccessRate) {
        this.quizSuccessRate = quizSuccessRate;
    }
}