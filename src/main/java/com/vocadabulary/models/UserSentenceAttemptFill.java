// UserSentenceAttemptFill.java
package com.vocadabulary.models;

import jakarta.persistence.*;

@Entity @Table(name = "user_sentence_attempt_fills")
public class UserSentenceAttemptFill {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long attemptId;
    private int blankIndex;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String typedWord;

    private Boolean isCorrect;

    @Column(name = "matched_flashcard_id")
    private Long matchedFlashcardId; // nullable

  // getters/setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(Long attemptId) {
        this.attemptId = attemptId;
    }

    public int getBlankIndex() {
        return blankIndex;
    }

    public void setBlankIndex(int blankIndex) {
        this.blankIndex = blankIndex;
    }

    public String getTypedWord() {
        return typedWord;
    }

    public void setTypedWord(String typedWord) {
        this.typedWord = typedWord;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Long getMatchedFlashcardId() {
        return matchedFlashcardId;
    }

    public void setMatchedFlashcardId(Long matchedFlashcardId) {
        this.matchedFlashcardId = matchedFlashcardId;
    }
}
