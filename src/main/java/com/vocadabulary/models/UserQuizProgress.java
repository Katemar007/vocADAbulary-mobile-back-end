package com.vocadabulary.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_quiz_progress")
public class UserQuizProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Reference to User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Reference to Quiz
    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(name = "current_question_index", nullable = false)
    private int currentQuestionIndex;

    // e.g. "in_progress", "completed", "failed"
    @Column(nullable = false)
    private String status;

    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;

    // Constructors
    public UserQuizProgress() {}

    public UserQuizProgress(User user, Quiz quiz, int currentQuestionIndex, String status, LocalDateTime lastUpdated) {
        this.user = user;
        this.quiz = quiz;
        this.currentQuestionIndex = currentQuestionIndex;
        this.status = status;
        this.lastUpdated = lastUpdated;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public void setCurrentQuestionIndex(int currentQuestionIndex) {
        this.currentQuestionIndex = currentQuestionIndex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}