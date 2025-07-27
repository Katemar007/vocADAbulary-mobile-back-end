package com.vocadabulary.services;

import com.vocadabulary.models.UserProgressSummary;
import com.vocadabulary.repositories.UserProgressSummaryRepository;
import com.vocadabulary.repositories.UserFlashcardRepository; // you need this
import com.vocadabulary.repositories.UserRepository; // <-- Add this import
import com.vocadabulary.models.User;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Service
public class UserProgressSummaryService {

    private final UserProgressSummaryRepository summaryRepo;
    private final UserFlashcardRepository userFlashcardRepo;
    private final UserRepository userRepo;
    

    public UserProgressSummaryService(UserProgressSummaryRepository summaryRepo,
                                      UserFlashcardRepository userFlashcardRepo,
                                      UserRepository userRepo) {
        this.summaryRepo = summaryRepo;
        this.userFlashcardRepo = userFlashcardRepo;
        this.userRepo = userRepo;
    }

    public UserProgressSummary getOrCreateSummary(Long userId) {
        // Load summary or create a new one if not found
        UserProgressSummary summary = summaryRepo.findById(userId)
            .orElseGet(() -> {
                User user = userRepo.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
                UserProgressSummary s = new UserProgressSummary();
                s.setUser(user);
                return summaryRepo.save(s);
            });

        // --- Calculate flashcard stats dynamically ---
        int total = userFlashcardRepo.countByUserId(userId);
        int learned = userFlashcardRepo.countByUserIdAndStatus(userId, "LEARNED");
        int inProgress = userFlashcardRepo.countByUserIdAndStatus(userId, "IN_PROGRESS");

        summary.setTotalFlashcards(total);
        summary.setLearnedFlashcards(learned);
        summary.setInProgressFlashcards(inProgress);

        // Quizzes and sentences remain as is (placeholders)
        return summary;
    }

/**
     * Updates timestamps (lastActive and updatedAt) without recalculating counts.
     * Use this for lightweight updates when user just views a flashcard etc.
     */
    public void refreshLastActive(Long userId) {
        UserProgressSummary summary = summaryRepo.findById(userId)
            .orElseGet(() -> {
                User user = userRepo.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));
                UserProgressSummary s = new UserProgressSummary();
                s.setUserId(userId);
                return s;
            });

        summary.setLastActive(LocalDate.now());
        summary.setUpdatedAt(LocalDateTime.now());

        summaryRepo.save(summary);
    }

    /**
     * Recalculates counts AND updates timestamps.
     * Use this when a user changes flashcard status, adds to wallet, etc.
     */
    public void recalculateSummary(Long userId) {
        UserProgressSummary summary = getOrCreateSummary(userId);

        summary.setLastActive(LocalDate.now());
        summary.setUpdatedAt(LocalDateTime.now());

        summaryRepo.save(summary);
    }
}
