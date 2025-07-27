package com.vocadabulary.dto;

public class FlashcardRequest {
    private Long topicId;
    private String word;
    private String definition;
    private String example;
    private String synonyms;
    private String phonetic;
    private String audioUrl;

    // Getters and setters
    public Long getTopicId() { return topicId; }
    public void setTopicId(Long topicId) { this.topicId = topicId; }

    public String getWord() { return word; }
    public void setWord(String word) { this.word = word; }

    public String getDefinition() { return definition; }
    public void setDefinition(String definition) { this.definition = definition; }

    public String getExample() { return example; }
    public void setExample(String example) { this.example = example; }

    public String getSynonyms() { return synonyms; }
    public void setSynonyms(String synonyms) { this.synonyms = synonyms; }

    public String getPhonetic() { return phonetic; }
    public void setPhonetic(String phonetic) { this.phonetic = phonetic; }

    public String getAudioUrl() { return audioUrl; }
    public void setAudioUrl(String audioUrl) { this.audioUrl = audioUrl; }
}