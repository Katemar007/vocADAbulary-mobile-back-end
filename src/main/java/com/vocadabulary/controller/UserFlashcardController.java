package com.vocadabulary.controller;

import com.vocadabulary.model.UserFlashcard;
import com.vocadabulary.repository.UserFlashcardRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/flashcards")
public class UserFlashcardController {

    private final UserFlashcardRepository userFlashcardRepository;

    public UserFlashcardController(UserFlashcardRepository userFlashcardRepository) {
        this.userFlashcardRepository = userFlashcardRepository;
    }

    @GetMapping
    public List<UserFlashcard> getAllFlashcardsForUser(@PathVariable Long userId) {
        return userFlashcardRepository.findByUserId(userId);
    }
}