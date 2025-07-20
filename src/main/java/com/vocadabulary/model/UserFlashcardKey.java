package com.vocadabulary.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserFlashcardKey implements Serializable {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "flashcard_id")
    private Long flashcardId;

    public UserFlashcardKey() {}

    public UserFlashcardKey(Long userId, Long flashcardId) {
        this.userId = userId;
        this.flashcardId = flashcardId;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserFlashcardKey)) return false;
        UserFlashcardKey that = (UserFlashcardKey) o;
        return Objects.equals(userId, that.userId) &&
               Objects.equals(flashcardId, that.flashcardId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, flashcardId);
    }
}