package com.vocadabulary.dto;

public class QuizDetailDTO {
    private Long id;
    private Long topicId;
    private String questionText;
    private String correctAnswer;
    private String wrongAnswer1;
    private String wrongAnswer2;
    private String wrongAnswer3;

    public QuizDetailDTO(Long id, Long topicId, String questionText, String correctAnswer, String wrongAnswer1, String wrongAnswer2, String wrongAnswer3) {
        this.id = id;
        this.topicId = topicId;
        this.questionText = questionText;
        this.correctAnswer = correctAnswer;
        this.wrongAnswer1 = wrongAnswer1;
        this.wrongAnswer2 = wrongAnswer2;
        this.wrongAnswer3 = wrongAnswer3;
    }

    public Long getId() { return id; }
    public Long getTopicId() { return topicId; }
    public String getQuestionText() { return questionText; }
    public String getCorrectAnswer() { return correctAnswer; }
    public String getWrongAnswer1() { return wrongAnswer1; }
    public String getWrongAnswer2() { return wrongAnswer2; }
    public String getWrongAnswer3() { return wrongAnswer3; }
}