// SentenceController.java
package com.vocadabulary.controllers;

import com.vocadabulary.auth.MockUserContext;
import com.vocadabulary.dto.SentenceDTOs.*;
import com.vocadabulary.dto.TemplateDTOs.TemplateResponseWithBlank;
import com.vocadabulary.services.SentenceService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sentences")
@CrossOrigin(origins = "*")
public class SentenceController {
    private long currentUserId() {
        return MockUserContext.getCurrentUser().getId();
    }
    private final SentenceService service;
    public SentenceController(SentenceService service){ this.service = service; }
    // private final Logger logger = LoggerFactory.getLogger(SentenceController.class);

    @GetMapping("/templates/{id}/prepare")
    public PrepareSentenceResponse prepare(@PathVariable Long id) {
        return service.prepareSentence(id);
    }

    @PostMapping("/finalize")
    public FinalizeSentenceResponse finalize(@RequestBody FinalizeSentenceRequest req) {
        return service.finalizeSentence(req);
    }

    @GetMapping("/templates/random")
    public TemplateResponseWithBlank getRandomTemplate() {
        return service.getRandomTemplateForUser(currentUserId());
}
}

