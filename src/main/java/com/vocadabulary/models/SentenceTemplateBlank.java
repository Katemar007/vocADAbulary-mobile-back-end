// SentenceTemplateBlank.java
package com.vocadabulary.models;

import jakarta.persistence.*;

@Entity @Table(name = "sentence_template_blanks",
    uniqueConstraints = @UniqueConstraint(columnNames = {"template_id","blank_index"}))
public class SentenceTemplateBlank {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "template_id", nullable = false)
  private Long templateId;

  @Column(name = "blank_index", nullable = false)
  private int blankIndex;

  @Column(name = "target_flashcard_id")
  private Long targetFlashcardId; // nullable

  // getters/setters

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getTemplateId() {
        return templateId;
    }
    
    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
    }
    
    public int getBlankIndex() {
        return blankIndex;
    }
    
    public void setBlankIndex(int blankIndex) {
        this.blankIndex = blankIndex;
    }
    
    public Long getTargetFlashcardId() {
        return targetFlashcardId;
    }
    
    public void setTargetFlashcardId(Long targetFlashcardId) {
        this.targetFlashcardId = targetFlashcardId;
    }
}
    