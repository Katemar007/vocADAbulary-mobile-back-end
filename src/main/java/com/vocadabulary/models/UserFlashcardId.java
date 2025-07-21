package com.vocadabulary.models;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserFlashcardId implements Serializable {

    private Long userId;
    private Long flashcardId;

    public UserFlashcardId() {}

    public UserFlashcardId(Long userId, Long flashcardId) {
        this.userId = userId;
        this.flashcardId = flashcardId;
    }

    // Getters and setters

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getFlashcardId() {
        return flashcardId;
    }

    public void setFlashcardId(Long flashcardId) {
        this.flashcardId = flashcardId;
    }

    // Override equals() and hashCode() for proper key comparison

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserFlashcardId)) return false;
        UserFlashcardId that = (UserFlashcardId) o;
        return Objects.equals(userId, that.userId) &&
            Objects.equals(flashcardId, that.flashcardId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, flashcardId);
    }
}