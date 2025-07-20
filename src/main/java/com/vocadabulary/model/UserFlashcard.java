package com.vocadabulary.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class UserFlashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "flashcard_id")
    private Flashcard flashcard;

    private boolean learned = false;

    @Column(name = "last_reviewed_at")
    private LocalDateTime lastReviewedAt;

    public UserFlashcard() {}

    public UserFlashcard(User user, Flashcard flashcard, boolean learned, LocalDateTime lastReviewedAt) {
        this.user = user;
        this.flashcard = flashcard;
        this.learned = learned;
        this.lastReviewedAt = lastReviewedAt;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Flashcard getFlashcard() {
        return flashcard;
    }

    public boolean isLearned() {
        return learned;
    }

    public LocalDateTime getLastReviewedAt() {
        return lastReviewedAt;
    }

    // Setters
    public void setUser(User user) {
        this.user = user;
    }

    public void setFlashcard(Flashcard flashcard) {
        this.flashcard = flashcard;
    }

    public void setLearned(boolean learned) {
        this.learned = learned;
    }

    public void setLastReviewedAt(LocalDateTime lastReviewedAt) {
        this.lastReviewedAt = lastReviewedAt;
    }
}