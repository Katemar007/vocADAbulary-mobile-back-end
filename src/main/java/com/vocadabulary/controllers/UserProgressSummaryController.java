package com.vocadabulary.controllers;

import com.vocadabulary.dto.UserProgressSummaryDTO;
import com.vocadabulary.services.UserProgressSummaryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users/{userId}/summary")
@CrossOrigin(origins = "*")
public class UserProgressSummaryController {

    private final UserProgressSummaryService userProgressSummaryService;

    public UserProgressSummaryController(UserProgressSummaryService userProgressSummaryService) {
        this.userProgressSummaryService = userProgressSummaryService;
    }

    @GetMapping
    public UserProgressSummaryDTO getUserProgressSummary(@PathVariable Long userId) {
        return userProgressSummaryService.getUserProgressSummary(userId);
    }
}