package com.vocadabulary.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Flashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    @JsonBackReference
    private Topic topic;

    private String word;
    private String definition;
    private String example;
    private String synonyms;
    private String phonetic;

    @Column(name = "audio_url")
    private String audioUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Flashcard() {}

    public Flashcard(Topic topic, String word, String definition, String example,
                     String synonyms, String phonetic, String audioUrl, LocalDateTime createdAt) {
        this.topic = topic;
        this.word = word;
        this.definition = definition;
        this.example = example;
        this.synonyms = synonyms;
        this.phonetic = phonetic;
        this.audioUrl = audioUrl;
        this.createdAt = createdAt;
    }

    // add public getters
    public Long getId() {
        return id;
    }

    public Topic getTopic() {
        return topic;
    }

    public String getWord() {
        return word;
    }

    public String getDefinition() {
        return definition;
    }

    public String getExample() {
        return example;
    }

    public String getSynonyms() {
        return synonyms;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}