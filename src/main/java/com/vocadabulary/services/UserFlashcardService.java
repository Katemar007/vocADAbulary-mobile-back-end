package com.vocadabulary.services;

import com.vocadabulary.models.UserFlashcard;
import com.vocadabulary.repositories.UserFlashcardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.vocadabulary.dto.WalletFlashcardDTO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserFlashcardService {
    private final UserFlashcardRepository userFlashcardRepo;



    public UserFlashcardService(UserFlashcardRepository userFlashcardRepo) {
        this.userFlashcardRepo = userFlashcardRepo;
    }

    @Transactional
    public void updateFlashcardStatus(Long userId, Long flashcardId, String newStatus) {
        var id = new com.vocadabulary.models.UserFlashcardId(userId, flashcardId);

        UserFlashcard userFlashcard = userFlashcardRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Flashcard not found for user"));

        userFlashcard.setStatus(newStatus);
        userFlashcard.setLastReviewed(java.time.LocalDateTime.now());
        userFlashcardRepo.save(userFlashcard);
    }

    // --- Basic Getters ---

    public List<UserFlashcard> getAllByUserId(Long userId) {
        return userFlashcardRepo.findByUserId(userId);
    }

    public List<UserFlashcard> getFlashcardsByUserIdAndStatus(Long userId, String status) {
        return userFlashcardRepo.findByUserIdAndStatusAndIsHiddenFalse(userId, status);
    }

    // --- Wallet-specific methods ---

    /**
     * Returns flashcards currently in the user's wallet
     */
    public List<WalletFlashcardDTO> getWalletFlashcards(Long userId) {
        var t = userFlashcardRepo.findByUserIdAndInWalletTrueAndIsHiddenFalse(userId);
        return t.stream()
                .map(uf -> new WalletFlashcardDTO(
                        uf.getFlashcard().getId(),
                        uf.getFlashcard().getWord(),
                        uf.getFlashcard().getDefinition(),
                        uf.getStatus(),
                        uf.getLastReviewed()
                ))
                .collect(Collectors.toList());
    }
    /**
     * Returns learned flashcards for this user (regardless of wallet)
     */
    public List<WalletFlashcardDTO> getLearnedFlashcards(Long userId) {
        return userFlashcardRepo.findLearnedAndVisibleFlashcards(userId)
                .stream()
                .map(uf -> new WalletFlashcardDTO(
                        uf.getFlashcard().getId(),
                        uf.getFlashcard().getWord(),
                        uf.getFlashcard().getDefinition(),
                        uf.getStatus(),
                        uf.getLastReviewed()
                ))
                .collect(Collectors.toList());
    }

    // GET In-progress flashcards
    public List<WalletFlashcardDTO> getInProgressFlashcards(Long userId) {
    return userFlashcardRepo.findByUserIdAndStatusAndIsHiddenFalse(userId, "IN_PROGRESS")
            .stream()
            .map(uf -> new WalletFlashcardDTO(
                    uf.getFlashcard().getId(),
                    uf.getFlashcard().getWord(),
                    uf.getFlashcard().getDefinition(),
                    uf.getStatus(),
                    uf.getLastReviewed()
            ))
            .collect(Collectors.toList());
    }
    // Hide a flashcard (sets isHidden=true)
        @Transactional
    public void hideFlashcard(Long userId, Long flashcardId) {
        var id = new com.vocadabulary.models.UserFlashcardId(userId, flashcardId);
        UserFlashcard uf = userFlashcardRepo.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Flashcard not found"));
        uf.setHidden(true);
        userFlashcardRepo.save(uf);
    }
    
    /** Remove a flashcard from the wallet (sets inWallet=false) */
    @DeleteMapping("/{flashcardId}/wallet")
    public Map<String, String> removeFromWallet(
            @PathVariable Long userId,
            @PathVariable Long flashcardId) {

        userFlashcardRepo.setInWalletFalse(userId, flashcardId);
        return Map.of("message", "Flashcard removed from wallet.");
    }

    /**
     * Add a flashcard to the wallet (if already exists, just set inWallet=true)
     */
    @Transactional
    public void addToWallet(UserFlashcard userFlashcard) {
        // If the row exists, just set inWallet to true
        if (userFlashcardRepo.existsByUserIdAndFlashcardId(
                userFlashcard.getUser().getId(),
                userFlashcard.getFlashcard().getId())) {

            UserFlashcard existing = userFlashcardRepo.findById(userFlashcard.getId())
                                                      .orElseThrow();
            existing.setInWallet(true);
            userFlashcardRepo.save(existing);
        } else {
            // Otherwise create a new record
            userFlashcard.setInWallet(true);
            userFlashcardRepo.save(userFlashcard);
        }
    }
}