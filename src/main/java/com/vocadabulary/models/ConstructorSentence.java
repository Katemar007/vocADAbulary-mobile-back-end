package com.vocadabulary.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "constructor_sentences")
public class ConstructorSentence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String sentenceTemplate;

    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @OneToMany(mappedBy = "sentence", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ConstructorSentenceBlank> blanks;

    public Long getId() { return id; }
    public String getSentenceTemplate() { return sentenceTemplate; }
    public Topic getTopic() { return topic; }
    public List<ConstructorSentenceBlank> getBlanks() { return blanks; }
}
