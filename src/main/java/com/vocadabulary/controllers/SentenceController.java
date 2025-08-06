// SentenceController.java
package com.vocadabulary.controllers;

import com.vocadabulary.dto.SentenceDTOs.*;
import com.vocadabulary.services.SentenceService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sentences")
@CrossOrigin(origins = "*")
public class SentenceController {
    private final SentenceService service;
    public SentenceController(SentenceService service){ this.service = service; }

    @GetMapping("/templates/{id}/prepare")
    public PrepareSentenceResponse prepare(@PathVariable Long id, @RequestHeader("X-Mock-User-Id") Long userId) {
        return service.prepareSentence(id, userId);
    }

    @PostMapping("/finalize")
    public FinalizeSentenceResponse finalize(@RequestBody FinalizeSentenceRequest req, @RequestHeader("X-Mock-User-Id") Long userId) {
        return service.finalizeSentence(req, userId);
    }
}
