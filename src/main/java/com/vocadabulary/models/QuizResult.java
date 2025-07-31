package com.vocadabulary.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "quiz_results")
public class QuizResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "quiz_id")
    private Long quizId;

    @Column(name = "taken_at")
    private LocalDateTime takenAt;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Column(name = "streak_count")
    private Integer streakCount;

    @Column(name = "hidden")
    private Boolean hidden;

    // Getters and Setters
}