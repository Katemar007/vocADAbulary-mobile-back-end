package com.vocadabulary.models;

import jakarta.persistence.*;

@Entity
@Table(name = "quiz_answers")
public class QuizAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relationships
    @ManyToOne
    @JoinColumn(name = "quiz_result_id", nullable = false)
    private QuizResult quizResult;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private QuizQuestion question;

    @Column(name = "selected_answer", nullable = false)
    private String selectedAnswer;

    @Column(name = "is_correct", nullable = false)
    private boolean isCorrect;

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public QuizResult getQuizResult() {
        return quizResult;
    }

    public void setQuizResult(QuizResult quizResult) {
        this.quizResult = quizResult;
    }

    public QuizQuestion getQuestion() {
        return question;
    }

    public void setQuestion(QuizQuestion question) {
        this.question = question;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}