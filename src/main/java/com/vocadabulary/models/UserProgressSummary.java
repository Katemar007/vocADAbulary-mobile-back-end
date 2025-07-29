package com.vocadabulary.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "user_progress_summary")
public class UserProgressSummary {

    @Id
    @Column(name = "user_id")
    private Long userId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    @JsonBackReference // Prevents infinite recursion during serialization
    private User user;

    @Column(name = "total_flashcards")
    private int totalFlashcards;

    @Column(name = "learned_flashcards")
    private int learnedFlashcards;

@Column(name = "in_progress_flashcards")
private int inProgressFlashcards;

    @Column(name = "quizzes_attempted")
    private int quizzesAttempted;

    @Column(name = "quizzes_passed")
    private int quizzesPassed;

    @Column(name = "quiz_success_rate")
    private float quizSuccessRate;

    @Column(name = "sentences_built")
    private int sentencesBuilt;

    @Column(name = "last_active")
    private LocalDate lastActive;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // === Getters & Setters ===

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getTotalFlashcards() {
        return totalFlashcards;
    }

    public void setTotalFlashcards(int totalFlashcards) {
        this.totalFlashcards = totalFlashcards;
    }

    public int getLearnedFlashcards() {
        return learnedFlashcards;
    }

    public void setLearnedFlashcards(int learnedFlashcards) {
        this.learnedFlashcards = learnedFlashcards;
    }

    public int getInProgressFlashcards() {
        return inProgressFlashcards;
    }

    public void setInProgressFlashcards(int inProgressFlashcards) {
        this.inProgressFlashcards = inProgressFlashcards;
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

    public int getSentencesBuilt() {
        return sentencesBuilt;
    }

    public void setSentencesBuilt(int sentencesBuilt) {
        this.sentencesBuilt = sentencesBuilt;
    }

    public LocalDate getLastActive() {
        return lastActive;
    }

    public void setLastActive(LocalDate lastActive) {
        this.lastActive = lastActive;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // === Constructors ===

    public UserProgressSummary() {}

    public UserProgressSummary(Long userId, int totalFlashcards, int learnedFlashcards, int inProgressFlashcards,
                               int quizzesAttempted, int quizzesPassed, float quizSuccessRate,
                               int sentencesBuilt, LocalDate lastActive, LocalDateTime updatedAt) {
        this.userId = userId;
        this.totalFlashcards = totalFlashcards;
        this.learnedFlashcards = learnedFlashcards;
        this.inProgressFlashcards = inProgressFlashcards;
        this.quizzesAttempted = quizzesAttempted;
        this.quizzesPassed = quizzesPassed;
        this.quizSuccessRate = quizSuccessRate;
        this.sentencesBuilt = sentencesBuilt;
        this.lastActive = lastActive;
        this.updatedAt = updatedAt;
    }
}