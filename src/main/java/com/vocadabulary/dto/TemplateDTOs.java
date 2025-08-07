// TemplateDTOs.java (basic admin CRUD)
package com.vocadabulary.dto;

import java.time.Instant;

public class TemplateDTOs {
    public static class CreateTemplateRequest {
        public String templateText;
        public String contextTopic;       // optional text
    }
    public static class UpdateTemplateRequest {
        public String templateText;
        public String contextTopic;
    }
    public static class TemplateResponse {
        public Long id;
        public String templateText;
        public String source;
        public String contextTopic;
        public Instant createdAt;

        public TemplateResponse(Long id, String templateText, String source, String contextTopic, Instant createdAt) {
            this.id = id;
            this.templateText = templateText;
            this.source = source;
            this.contextTopic = contextTopic;
            this.createdAt = createdAt;
        }
    }

    public static class TemplateResponseWithBlank extends TemplateResponse {
    public Long flashcardId;
    public int blankIndex;

    public TemplateResponseWithBlank(
        Long id,
        String templateText,
        String source,
        String contextTopic,
        Instant createdAt,
        Long flashcardId,
        int blankIndex
    ) {
        super(id, templateText, source, contextTopic, createdAt);
        this.flashcardId = flashcardId;
        this.blankIndex = blankIndex;
    }
}
}
