package com.vocadabulary.dto;

import java.time.LocalDateTime;

public class WalletFlashcardDTO {
    private Long flashcardId;
    private String word;
    private String definition;
    private String status;
    private LocalDateTime lastReviewed;

    public WalletFlashcardDTO(Long flashcardId, String word, String definition,
                              String status, LocalDateTime lastReviewed) {
        this.flashcardId = flashcardId;
        this.word = word;
        this.definition = definition;
        this.status = status;
        this.lastReviewed = lastReviewed;
    }

    public Long getFlashcardId() {
        return flashcardId;
    }

    public String getWord() {
        return word;
    }

    public String getDefinition() {
        return definition;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getLastReviewed() {
        return lastReviewed;
    }
}