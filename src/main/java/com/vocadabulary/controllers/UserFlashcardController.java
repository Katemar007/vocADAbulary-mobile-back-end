package com.vocadabulary.controllers;

import com.vocadabulary.models.UserFlashcard;
import com.vocadabulary.services.UserFlashcardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users/{userId}/flashcards")
public class UserFlashcardController {

    private final UserFlashcardService userFlashcardService;

    public UserFlashcardController(UserFlashcardService userFlashcardService) {
        this.userFlashcardService = userFlashcardService;
    }

    @GetMapping
    public List<UserFlashcard> getUserFlashcards(@PathVariable Long userId) {
        return userFlashcardService.getAllByUserId(userId);
    }

    @GetMapping("/in-progress")
    public List<UserFlashcard> getInProgressFlashcards(@PathVariable Long userId) {
        return userFlashcardService.getFlashcardsByUserIdAndStatus(userId, "IN_PROGRESS");
    }

    @GetMapping("/favorites")
    public List<UserFlashcard> getFavoriteFlashcards(@PathVariable Long userId) {
        return userFlashcardService.getFlashcardsByUserIdAndStatus(userId, "FAVORITE");
    }
}
