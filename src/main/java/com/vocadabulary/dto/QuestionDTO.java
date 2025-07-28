package com.vocadabulary.dto;

import java.util.List;

public class QuestionDTO {
    private Long id;
    private String questionText;
    private List<AnswerDTO> answers;

    public QuestionDTO(Long id, String questionText, List<AnswerDTO> answers) {
        this.id = id;
        this.questionText = questionText;
        this.answers = answers;
    }

    public Long getId() {
        return id;
    }

    public String getQuestionText() {
        return questionText;
    }

    public List<AnswerDTO> getAnswers() {
        return answers;
    }

    public static class AnswerDTO {
        private String text;
        private boolean isCorrect;

        public AnswerDTO(String text, boolean isCorrect) {
            this.text = text;
            this.isCorrect = isCorrect;
        }

        public String getText() {
            return text;
        }

        public boolean isCorrect() {
            return isCorrect;
        }
    }
}