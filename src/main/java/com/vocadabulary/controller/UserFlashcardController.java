package com.vocadabulary.controller;

import com.vocadabulary.model.UserFlashcard;
import com.vocadabulary.model.FlashcardStatus;
import com.vocadabulary.repository.UserFlashcardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users/{userId}/flashcards")
public class UserFlashcardController {

    @Autowired
    private UserFlashcardRepository userFlashcardRepository;

    @GetMapping("/favorites")
    public List<UserFlashcard> getFavoriteFlashcards(@PathVariable Long userId) {
        return userFlashcardRepository.findByUserIdAndStatus(userId, FlashcardStatus.FAVORITE);
    }

    @GetMapping("/learned")
    public List<UserFlashcard> getLearnedFlashcards(@PathVariable Long userId) {
        return userFlashcardRepository.findByUserIdAndStatus(userId, FlashcardStatus.LEARNED);
    }

    @GetMapping("/in-progress")
    public List<UserFlashcard> getInProgressFlashcards(@PathVariable Long userId) {
        return userFlashcardRepository.findByUserIdAndStatus(userId, FlashcardStatus.IN_PROGRESS);
    }
}