package com.vocadabulary.controllers;

import com.vocadabulary.models.UserFlashcard;
import com.vocadabulary.services.UserFlashcardService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/users/{userId}/flashcards")
public class UserFlashcardController {

    private final UserFlashcardService userFlashcardService;

    public UserFlashcardController(UserFlashcardService userFlashcardService) {
        this.userFlashcardService = userFlashcardService;
    }

    @GetMapping
    public List<UserFlashcard> getUserFlashcards(@PathVariable Long userId) {
        return userFlashcardService.getAllByUserId(userId);
    }

    @GetMapping("/in_progress")
    public List<UserFlashcard> getInProgressFlashcards(@PathVariable Long userId) {
        return userFlashcardService.getFlashcardsByUserIdAndStatus(userId, "IN_PROGRESS");
    }

    @GetMapping("/learned")
    public List<UserFlashcard> getLearnedFlashcards(@PathVariable Long userId) {
        return userFlashcardService.getLearnedFlashcards(userId);
    }

    // Get flashcards in the user's wallet
    @GetMapping("/wallet")
    public List<com.vocadabulary.dto.WalletFlashcardDTO> getWalletFlashcards(
            @PathVariable Long userId
    ) {
        return userFlashcardService.getWalletFlashcards(userId);
    }

    /** Remove a flashcard from the wallet (sets inWallet=false) */
    @DeleteMapping("/{flashcardId}/wallet")
    public Map<String, String> removeFromWallet(
            @PathVariable Long userId,
            @PathVariable Long flashcardId) {

        userFlashcardService.removeFromWallet(userId, flashcardId);
        return Map.of("message", "Flashcard removed from wallet.");
    }

    /** Update the learning status of a flashcard (IN_PROGRESS, LEARNED, etc.) */
    @PutMapping("/{flashcardId}/status")
    public Map<String, String> updateFlashcardStatus(
            @PathVariable Long userId,
            @PathVariable Long flashcardId,
            @RequestBody Map<String, String> body) {

        String newStatus = body.get("status");
        userFlashcardService.updateFlashcardStatus(userId, flashcardId, newStatus);
        return Map.of("message", "Flashcard status updated to " + newStatus);
    }
}
