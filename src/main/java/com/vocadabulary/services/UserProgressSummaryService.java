package com.vocadabulary.services;

import com.vocadabulary.dto.UserProgressSummaryDTO;
import com.vocadabulary.models.UserQuizSummary;
import com.vocadabulary.repositories.FlashcardRepository;
import com.vocadabulary.repositories.UserFlashcardRepository;
import com.vocadabulary.repositories.UserProgressSummaryRepository;
import com.vocadabulary.repositories.UserQuizSummaryRepository;
import org.springframework.stereotype.Service;

@Service
public class UserProgressSummaryService {

    private final FlashcardRepository flashcardRepo;
    private final UserFlashcardRepository userFlashcardRepo;
    private final UserProgressSummaryRepository userProgressSummaryRepo;
    private final UserQuizSummaryRepository userQuizSummaryRepo; // <-- ADD THIS

    // Update constructor
    public UserProgressSummaryService(
        FlashcardRepository flashcardRepo,
        UserFlashcardRepository userFlashcardRepo,
        UserProgressSummaryRepository userProgressSummaryRepo,
        UserQuizSummaryRepository userQuizSummaryRepo // <-- ADD THIS
    ) {
        this.flashcardRepo = flashcardRepo;
        this.userFlashcardRepo = userFlashcardRepo;
        this.userProgressSummaryRepo = userProgressSummaryRepo;
        this.userQuizSummaryRepo = userQuizSummaryRepo; // <-- ADD THIS
    }

    public void refreshLastActive(long userId) {
        // Leave as is, or remove if unused
    }

    public UserProgressSummaryDTO getUserProgressSummary(Long userId) {
        long totalCards = flashcardRepo.countAllFlashcards();
        long inProgress = userProgressSummaryRepo.countInProgressByUser(userId);
        long learned = userProgressSummaryRepo.countLearnedByUser(userId);

        // Get quizzes passed (from user_quiz_summary)
        int quizzesPassed = 0;
        UserQuizSummary quizSummary = userQuizSummaryRepo.findById(userId).orElse(null);
        if (quizSummary != null) {
            quizzesPassed = quizSummary.getQuizzesPassed();
        }

        return new UserProgressSummaryDTO(
            totalCards,
            inProgress,
            learned,
            "Placeholder comprehension",
            "Placeholder language use",
            quizzesPassed      // <-- NEW
        );
    }
}