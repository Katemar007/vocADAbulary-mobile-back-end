package com.vocadabulary.dto;

import java.time.LocalDateTime;

public class FlashcardDTO {
    private Long id;
    private String word;
    private String definition;
    private String example;
    private String synonyms;
    private String phonetic;
    private LocalDateTime createdAt;
    private Long createdBy;

    public FlashcardDTO(Long id, String word, String definition, String example, String synonyms, String phonetic, LocalDateTime createdAt, Long createdBy) {
        this.id = id;
        this.word = word;
        this.definition = definition;
        this.example = example;
        this.synonyms = synonyms;
        this.phonetic = phonetic;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    // Getters
    public Long getId() { return id; }
    public String getWord() { return word; }
    public String getDefinition() { return definition; }
    public String getExample() { return example; }
    public String getSynonyms() { return synonyms; }
    public String getPhonetic() { return phonetic; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public Long getCreatedBy() { return createdBy; }
}
