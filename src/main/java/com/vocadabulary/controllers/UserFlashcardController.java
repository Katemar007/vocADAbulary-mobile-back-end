package com.vocadabulary.controllers;

import com.vocadabulary.models.UserFlashcard;
import com.vocadabulary.services.UserFlashcardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
