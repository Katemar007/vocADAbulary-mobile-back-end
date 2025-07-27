package com.vocadabulary.dto;

import java.util.List;

public class QuizDTO {
    private Long id;
    private Long topicId;
    private String topicName;
    private List<QuestionDTO> questions;

    public QuizDTO(Long id, Long topicId, String topicName, List<QuestionDTO> questions) {
        this.id = id;
        this.topicId = topicId;
        this.topicName = topicName;
        this.questions = questions;
    }

    public Long getId() {
        return id;
    }

    public Long getTopicId() {
        return topicId;
    }

    public String getTopicName() {
        return topicName;
    }

    public List<QuestionDTO> getQuestions() {
        return questions;
    }
}