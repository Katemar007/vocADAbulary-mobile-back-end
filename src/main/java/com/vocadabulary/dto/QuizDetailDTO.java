package com.vocadabulary.dto;

import java.util.List;

public class QuizDetailDTO {
    private Long id;
    private Long topicId;
    private List<QuestionDTO> questions;

    public QuizDetailDTO(Long id, Long topicId, List<QuestionDTO> questions) {
        this.id = id;
        this.topicId = topicId;
        this.questions = questions;
    }

    public Long getId() { return id; }
    public Long getTopicId() { return topicId; }
    public List<QuestionDTO> getQuestions() { return questions; }
}