package com.vocadabulary.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "flashcards")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Flashcard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many flashcards belong to one topic
    @ManyToOne
    @JoinColumn(name = "topic_id")
    @JsonBackReference
    private Topic topic;

    @Column(nullable = false)
    private String word;

    @Column(columnDefinition = "TEXT")
    private String definition;

    @Column(columnDefinition = "TEXT")
    private String example;

    private String synonyms;

    private String phonetic;

    @Column(name = "audio_url")
    private String audioUrl;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    private Long createdBy;

    // audioBase64 is used to store the audio content in base64 format for easy transfer
    // This field is not persisted in the database, but used for transferring audio data
    @Transient
    private String audioBase64;

    public String getAudioBase64() {
        return audioBase64;
    }

    public void setAudioBase64(String audioBase64) {
        this.audioBase64 = audioBase64;
    }

    // Constructors
    public Flashcard() {}

    public Flashcard(Topic topic, String word, String definition, String example,
                    String synonyms, String phonetic, String audioUrl, LocalDateTime createdAt, Long createdBy) {
        this.topic = topic;
        this.word = word;
        this.definition = definition;
        this.example = example;
        this.synonyms = synonyms;
        this.phonetic = phonetic;
        this.audioUrl = audioUrl;
        this.createdAt = createdAt;
        this.createdBy = createdBy;
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDefinition() {
        return definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    public String getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(String synonyms) {
        this.synonyms = synonyms;
    }

    public String getPhonetic() {
        return phonetic;
    }

    public void setPhonetic(String phonetic) {
        this.phonetic = phonetic;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Long getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }
}