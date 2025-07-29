package com.vocadabulary.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String email;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    //  One User → Many UserFlashcards
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserFlashcard> userFlashcards;

    // One User → Many QuizResults
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuizResult> quizResults;

    //  One User → One ProgressSummary
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Prevents infinite recursion during serialization
    private UserProgressSummary userProgressSummary;

    //  One User → Many UserQuizProgress
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserQuizProgress> userQuizProgress;

    // === Getters & Setters ===

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<UserFlashcard> getUserFlashcards() {
        return userFlashcards;
    }

    public void setUserFlashcards(List<UserFlashcard> userFlashcards) {
        this.userFlashcards = userFlashcards;
    }

    public List<QuizResult> getQuizResults() {
        return quizResults;
    }

    public void setQuizResults(List<QuizResult> quizResults) {
        this.quizResults = quizResults;
    }

    public UserProgressSummary getUserProgressSummary() {
        return userProgressSummary;
    }

    public void setUserProgressSummary(UserProgressSummary userProgressSummary) {
        this.userProgressSummary = userProgressSummary;
    }

    public List<UserQuizProgress> getUserQuizProgress() {
        return userQuizProgress;
    }

    public void setUserQuizProgress(List<UserQuizProgress> userQuizProgress) {
        this.userQuizProgress = userQuizProgress;
    }
}