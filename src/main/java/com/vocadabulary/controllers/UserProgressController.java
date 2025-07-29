package com.vocadabulary.controllers;

import com.vocadabulary.models.UserProgressSummary;
import com.vocadabulary.services.UserProgressSummaryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserProgressController {

    private final UserProgressSummaryService summaryService;

    public UserProgressController(UserProgressSummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @GetMapping("/{id}/progress")
    public UserProgressSummary getProgress(@PathVariable Long id) {
        return summaryService.getOrCreateSummary(id);
    }
}