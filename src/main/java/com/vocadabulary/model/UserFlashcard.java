package com.vocadabulary.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_flashcards")
public class UserFlashcard {

    @EmbeddedId
    private UserFlashcardKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("flashcardId")
    @JoinColumn(name = "flashcard_id")
    private Flashcard flashcard;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private FlashcardStatus status;

    @Column(name = "last_reviewed")
    private LocalDateTime lastReviewed;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();

    public UserFlashcard() {}

    public UserFlashcard(User user, Flashcard flashcard, FlashcardStatus status, LocalDateTime lastReviewed) {
        this.user = user;
        this.flashcard = flashcard;
        this.status = status;
        this.lastReviewed = lastReviewed;
        this.id = new UserFlashcardKey(user.getId(), flashcard.getId());
    }

    public UserFlashcardKey getId() {
        return id;
    }

    public void setId(UserFlashcardKey id) {
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

    public FlashcardStatus getStatus() {
        return status;
    }

    public void setStatus(FlashcardStatus status) {
        this.status = status;
    }

    public LocalDateTime getLastReviewed() {
        return lastReviewed;
    }

    public void setLastReviewed(LocalDateTime lastReviewed) {
        this.lastReviewed = lastReviewed;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}