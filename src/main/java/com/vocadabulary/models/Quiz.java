package com.vocadabulary.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quizzes")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "topic_id")
    private Long topicId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "question_text")
    private String questionText;

    @Column(name = "correct_answer")
    private String correctAnswer;

    @Column(name = "wrong_answer_1")
    private String wrongAnswer1;

    @Column(name = "wrong_answer_2")
    private String wrongAnswer2;

    @Column(name = "wrong_answer_3")
    private String wrongAnswer3;

    // Getters and Setters
}