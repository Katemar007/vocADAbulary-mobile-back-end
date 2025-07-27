package com.vocadabulary.dto;

public class AnswerDTO {
    private Long id;
    private String text;
    private boolean isCorrect;

    public AnswerDTO(Long id, String text, boolean isCorrect) {
        this.id = id;
        this.text = text;
        this.isCorrect = isCorrect;
    }

    public Long getId() { return id; }
    public String getText() { return text; }
    public boolean isCorrect() { return isCorrect; }
}