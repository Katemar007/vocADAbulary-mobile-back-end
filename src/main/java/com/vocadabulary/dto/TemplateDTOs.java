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
}
