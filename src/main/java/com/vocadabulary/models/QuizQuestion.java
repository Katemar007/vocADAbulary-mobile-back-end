package com.vocadabulary.models;

import jakarta.persistence.*;

@Entity
@Table(name = "quiz_questions")
public class QuizQuestion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many questions belong to one quiz
    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    @Column(name = "question_text", nullable = false)
    private String questionText;

    @Column(name = "correct_answer", nullable = false)
    private String correctAnswer;

    @Column(name = "wrong_answer_1")
    private String wrongAnswer1;

    @Column(name = "wrong_answer_2")
    private String wrongAnswer2;

    @Column(name = "wrong_answer_3")
    private String wrongAnswer3;

    // Constructors
    public QuizQuestion() {}

    public QuizQuestion(Quiz quiz, String questionText, String correctAnswer,
                        String wrongAnswer1, String wrongAnswer2, String wrongAnswer3) {
        this.quiz = quiz;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.wrongAnswer1 = wrongAnswer1;
        this.wrongAnswer2 = wrongAnswer2;
        this.wrongAnswer3 = wrongAnswer3;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getWrongAnswer1() {
        return wrongAnswer1;
    }

    public void setWrongAnswer1(String wrongAnswer1) {
        this.wrongAnswer1 = wrongAnswer1;
    }

    public String getWrongAnswer2() {
        return wrongAnswer2;
    }

    public void setWrongAnswer2(String wrongAnswer2) {
        this.wrongAnswer2 = wrongAnswer2;
    }

    public String getWrongAnswer3() {
        return wrongAnswer3;
    }

    public void setWrongAnswer3(String wrongAnswer3) {
        this.wrongAnswer3 = wrongAnswer3;
    }
}
