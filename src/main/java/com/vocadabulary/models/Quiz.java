package com.vocadabulary.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "quizzes")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many quizzes belong to one topic
    @ManyToOne
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // One quiz has many questions
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuizQuestion> questions;

    // Constructors
    public Quiz() {}

    public Quiz(Topic topic, LocalDateTime createdAt) {
        this.topic = topic;
        this.createdAt = createdAt;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<QuizQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuizQuestion> questions) {
        this.questions = questions;
    }
}