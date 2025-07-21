package com.vocadabulary.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Column;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_flashcards")
public class UserFlashcard {

    @EmbeddedId
    private UserFlashcardId id;

    @ManyToOne
    @MapsId("userId")  // Maps userId attribute of embedded id
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("flashcardId")  // Maps flashcardId attribute of embedded id
    @JoinColumn(name = "flashcard_id")
    private Flashcard flashcard;

    @Column(nullable = false)
    private String status;

    @Column(name = "last_reviewed")
    private LocalDateTime lastReviewed;

    public UserFlashcard() {}

    public UserFlashcard(User user, Flashcard flashcard, String status, LocalDateTime lastReviewed) {
        this.user = user;
        this.flashcard = flashcard;
        this.status = status;
        this.lastReviewed = lastReviewed;
        this.id = new UserFlashcardId(user.getId(), flashcard.getId());
    }

    // Getters and setters

    public UserFlashcardId getId() {
        return id;
    }

    public void setId(UserFlashcardId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Flashcard getFlashcard() {
        return flashcard;
    }

    public void setFlashcard(Flashcard flashcard) {
        this.flashcard = flashcard;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getLastReviewed() {
        return lastReviewed;
    }

    public void setLastReviewed(LocalDateTime lastReviewed) {
        this.lastReviewed = lastReviewed;
    }
}