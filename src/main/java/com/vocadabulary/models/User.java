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

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;

    // One User → Many UserFlashcards
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<UserFlashcard> userFlashcards;

    // One User → One ProgressSummary
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private UserProgressSummary userProgressSummary;

    // One User → Many QuizAttempts
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<UserQuizAttempt> userQuizAttempts;

    // One User → One QuizSummary
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private UserQuizSummary userQuizSummary;

    // One User → Many QuizStatus
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<UserQuizStatus> userQuizStatuses;

    // === Getters & Setters ===

    public Long getId() { return id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<UserFlashcard> getUserFlashcards() { return userFlashcards; }
    public void setUserFlashcards(List<UserFlashcard> userFlashcards) { this.userFlashcards = userFlashcards; }

    public UserProgressSummary getUserProgressSummary() { return userProgressSummary; }
    public void setUserProgressSummary(UserProgressSummary userProgressSummary) { this.userProgressSummary = userProgressSummary; }

    public List<UserQuizAttempt> getUserQuizAttempts() { return userQuizAttempts; }
    public void setUserQuizAttempts(List<UserQuizAttempt> userQuizAttempts) { this.userQuizAttempts = userQuizAttempts; }

    public UserQuizSummary getUserQuizSummary() { return userQuizSummary; }
    public void setUserQuizSummary(UserQuizSummary userQuizSummary) { this.userQuizSummary = userQuizSummary; }

    public List<UserQuizStatus> getUserQuizStatuses() { return userQuizStatuses; }
    public void setUserQuizStatuses(List<UserQuizStatus> userQuizStatuses) { this.userQuizStatuses = userQuizStatuses; }
}