package com.vocadabulary.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_flashcards")
public class UserFlashcard {

    @EmbeddedId
    private UserFlashcardId id;

    @JsonIgnore
    @ManyToOne(optional = true)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore
    @ManyToOne(optional = true)
    @MapsId("flashcardId")
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