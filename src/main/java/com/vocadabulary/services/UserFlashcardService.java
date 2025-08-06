package com.vocadabulary.services;

import com.vocadabulary.models.UserFlashcard;
import com.vocadabulary.models.UserFlashcardId;
import com.vocadabulary.models.User;
import com.vocadabulary.models.Flashcard;
import com.vocadabulary.repositories.UserFlashcardRepository;
import com.vocadabulary.repositories.UserRepository;
import com.vocadabulary.repositories.FlashcardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.vocadabulary.dto.WalletFlashcardDTO;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserFlashcardService {
    private final UserFlashcardRepository userFlashcardRepo;
    private final UserRepository userRepo;
    private final FlashcardRepository flashcardRepo;

    public UserFlashcardService(
        UserFlashcardRepository userFlashcardRepo,
        UserRepository userRepo,
        FlashcardRepository flashcardRepo
    ) {
        this.userFlashcardRepo = userFlashcardRepo;
        this.userRepo = userRepo;
        this.flashcardRepo = flashcardRepo;
    }

    @Transactional
    public void updateFlashcardStatus(Long userId, Long flashcardId, String newStatus) {
        System.out.println("=== updateFlashcardStatus called with userId=" + userId + ", flashcardId=" + flashcardId + ", newStatus=" + newStatus);
        UserFlashcardId ufId = new UserFlashcardId(userId, flashcardId);

        UserFlashcard userFlashcard = userFlashcardRepo.findById(ufId)
            .orElseGet(() -> {
                System.out.println("   --> UserFlashcard does not exist yet. Creating new.");
                User user = userRepo.findById(userId)
                    .orElseThrow(() -> {
                        System.out.println("   --> USER NOT FOUND for id=" + userId);
                        return new IllegalArgumentException("User not found");
                    });
                System.out.println("   --> Found user: " + user);
                Flashcard flashcard = flashcardRepo.findById(flashcardId)
                    .orElseThrow(() -> {
                        System.out.println("   --> FLASHCARD NOT FOUND for id=" + flashcardId);
                        return new IllegalArgumentException("Flashcard not found");
                    });
                System.out.println("   --> Found flashcard: " + flashcard);
                UserFlashcard newUf = new UserFlashcard();
                newUf.setId(ufId); // <-- CRITICAL: Set the composite ID
                newUf.setUser(user);
                newUf.setFlashcard(flashcard);
                newUf.setStatus(newStatus != null ? newStatus : "IN_PROGRESS");
                newUf.setInWallet(false); // or true if needed
                newUf.setHidden(false);
                newUf.setLastReviewed(java.time.LocalDateTime.now());
                System.out.println("   --> Saving new UserFlashcard: " + newUf);
                return userFlashcardRepo.save(newUf);
            });

        System.out.println("   --> userFlashcard entity about to be updated: " + userFlashcard);
        userFlashcard.setStatus(newStatus);
        userFlashcard.setLastReviewed(java.time.LocalDateTime.now());
        userFlashcardRepo.save(userFlashcard);
        System.out.println("   --> userFlashcard updated and saved.");
    }

    // --- Basic Getters ---

    public List<UserFlashcard> getAllByUserId(Long userId) {
        System.out.println("   --> getAllByUserId called with userId=" + userId);
        return userFlashcardRepo.findByUserId(userId);
    }

    public List<UserFlashcard> getFlashcardsByUserIdAndStatus(Long userId, String status) {
        System.out.println("   --> getFlashcardsByUserIdAndStatus called with userId=" + userId + ", status=" + status);
        return userFlashcardRepo.findByUserIdAndStatusAndIsHiddenFalse(userId, status);
    }

    // --- Wallet-specific methods ---

    public List<WalletFlashcardDTO> getWalletFlashcards(Long userId) {
        System.out.println("   --> getWalletFlashcards called with userId=" + userId);
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

    public List<WalletFlashcardDTO> getLearnedFlashcards(Long userId) {
        System.out.println("   --> getLearnedFlashcards called with userId=" + userId);
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

    public List<WalletFlashcardDTO> getInProgressFlashcards(Long userId) {
        System.out.println("   --> getInProgressFlashcards called with userId=" + userId);
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

    @Transactional
    public void hideFlashcard(Long userId, Long flashcardId) {
        System.out.println("   --> hideFlashcard called with userId=" + userId + ", flashcardId=" + flashcardId);
        UserFlashcardId ufId = new UserFlashcardId(userId, flashcardId);
        UserFlashcard uf = userFlashcardRepo.findById(ufId)
            .orElseThrow(() -> {
                System.out.println("   --> hideFlashcard: Flashcard not found for user/card id!");
                return new IllegalArgumentException("Flashcard not found");
            });
        uf.setHidden(true);
        userFlashcardRepo.save(uf);
        System.out.println("   --> hideFlashcard: userFlashcard saved as hidden.");
    }
    
    /** Remove a flashcard from the wallet (sets inWallet=false) */
    @Transactional
    public void removeFromWallet(Long userId, Long flashcardId) {
        System.out.println("   --> removeFromWallet called with userId=" + userId + ", flashcardId=" + flashcardId);
        userFlashcardRepo.setInWalletFalse(userId, flashcardId);
        System.out.println("   --> removeFromWallet: setInWalletFalse called.");
    }

    /**
     * Add a flashcard to the wallet (create if not exists)
     */
    @Transactional
    public void addToWallet(Long userId, Long flashcardId) {
        System.out.println("=== addToWallet called with userId=" + userId + ", flashcardId=" + flashcardId);
        UserFlashcardId ufId = new UserFlashcardId(userId, flashcardId);

        UserFlashcard userFlashcard = userFlashcardRepo.findById(ufId)
            .orElseGet(() -> {
                System.out.println("   --> UserFlashcard does not exist yet. Creating new.");
                User user = userRepo.findById(userId)
                    .orElseThrow(() -> {
                        System.out.println("   --> USER NOT FOUND for id=" + userId);
                        return new IllegalArgumentException("User not found");
                    });
                System.out.println("   --> Found user: " + user);
                Flashcard flashcard = flashcardRepo.findById(flashcardId)
                    .orElseThrow(() -> {
                        System.out.println("   --> FLASHCARD NOT FOUND for id=" + flashcardId);
                        return new IllegalArgumentException("Flashcard not found");
                    });
                System.out.println("   --> Found flashcard: " + flashcard);
                UserFlashcard newUf = new UserFlashcard();
                newUf.setId(ufId); // <-- CRITICAL: Set the composite ID
                newUf.setUser(user);
                newUf.setFlashcard(flashcard);
                newUf.setStatus("IN_PROGRESS"); // default status
                newUf.setInWallet(true); // mark as in wallet!
                newUf.setHidden(false);
                newUf.setLastReviewed(java.time.LocalDateTime.now());
                System.out.println("   --> Saving new UserFlashcard: " + newUf);
                return userFlashcardRepo.save(newUf);
            });

        // Always mark as in wallet
        System.out.println("   --> userFlashcard entity about to be updated (set inWallet=true): " + userFlashcard);
        userFlashcard.setInWallet(true);
        userFlashcardRepo.save(userFlashcard);
        System.out.println("   --> userFlashcard updated and saved.");
    }
}