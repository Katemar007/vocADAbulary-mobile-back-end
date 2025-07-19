package com.vocadabulary.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Flashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "topic_id")
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

    // Constructor with fields
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

    // Getters and setters for all fields go here...
}

