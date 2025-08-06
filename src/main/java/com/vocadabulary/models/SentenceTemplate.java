package com.vocadabulary.models;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import java.time.Instant;
// SentenceTemplate.java

@Entity
@Table(name = "sentence_templates")
public class SentenceTemplate {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(columnDefinition = "TEXT", nullable = false)
  private String templateText;

  @Column(nullable = false)
  private String source = "manual"; // 'manual' | 'ai'

  private String contextTopic;      // metadata for AI provenance

  private Instant createdAt = Instant.now();

  // getters/setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTemplateText() {
        return templateText;
    }
    public void setTemplateText(String templateText) {
        this.templateText = templateText;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getContextTopic() {
        return contextTopic;
    }
    public void setContextTopic(String contextTopic) {
        this.contextTopic = contextTopic;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

}

