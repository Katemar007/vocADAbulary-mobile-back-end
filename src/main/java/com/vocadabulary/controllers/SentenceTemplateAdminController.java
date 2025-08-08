// SentenceTemplateAdminController.java (optional CRUD)
package com.vocadabulary.controllers;

import com.vocadabulary.dto.TemplateDTOs.*;
import com.vocadabulary.models.SentenceTemplate;
import com.vocadabulary.repositories.SentenceTemplateRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sentence-templates")
@CrossOrigin(origins = "*")
public class SentenceTemplateAdminController {
    private final SentenceTemplateRepository repo;
    public SentenceTemplateAdminController(SentenceTemplateRepository repo){ this.repo=repo; }

    @PostMapping
    public TemplateResponse create(@RequestBody CreateTemplateRequest req){
        SentenceTemplate t = new SentenceTemplate();
        t.setTemplateText(req.templateText);
        t.setContextTopic(req.contextTopic);
        t.setSource("manual");
        t = repo.save(t);
        return new TemplateResponse(t.getId(), t.getTemplateText(), t.getSource(), t.getContextTopic(), t.getCreatedAt());
    }

    @GetMapping
    public List<SentenceTemplate> list(){ return repo.findAll(); }
}
