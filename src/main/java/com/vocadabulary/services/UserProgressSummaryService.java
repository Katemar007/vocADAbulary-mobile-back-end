package com.vocadabulary.services;

import com.vocadabulary.dto.UserProgressSummaryDTO;
import com.vocadabulary.models.UserQuizSummary;
import com.vocadabulary.repositories.FlashcardRepository;
import com.vocadabulary.repositories.UserFlashcardRepository;
import com.vocadabulary.repositories.UserProgressSummaryRepository;
import com.vocadabulary.repositories.UserQuizSummaryRepository;
import com.vocadabulary.repositories.UserSentenceAttemptFillRepository;

import org.springframework.stereotype.Service;

@Service
public class UserProgressSummaryService {

    private final FlashcardRepository flashcardRepo;
    private final UserFlashcardRepository userFlashcardRepo;
    private final UserProgressSummaryRepository userProgressSummaryRepo;
    private final UserQuizSummaryRepository userQuizSummaryRepo;
    private final UserSentenceAttemptFillRepository userSentenceAttemptFillRepo;


    // Update constructor
    public UserProgressSummaryService(
        FlashcardRepository flashcardRepo,
        UserFlashcardRepository userFlashcardRepo,
        UserProgressSummaryRepository userProgressSummaryRepo,
        UserQuizSummaryRepository userQuizSummaryRepo,
        UserSentenceAttemptFillRepository userSentenceAttemptFillRepo
    ) {
        this.flashcardRepo = flashcardRepo;
        this.userFlashcardRepo = userFlashcardRepo;
        this.userProgressSummaryRepo = userProgressSummaryRepo;
        this.userQuizSummaryRepo = userQuizSummaryRepo;
        this.userSentenceAttemptFillRepo = userSentenceAttemptFillRepo;
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

        // sentence proficiency (%)
        long totalBlanks = userSentenceAttemptFillRepo.countTotalBlanksAttempted(userId);
        long firstTryCorrect = userSentenceAttemptFillRepo.countFirstTryCorrect(userId);
        float sentenceProficiency = (totalBlanks == 0) ? 0f
                : (firstTryCorrect * 100f / (float) totalBlanks);

        return new UserProgressSummaryDTO(
            totalCards,
            inProgress,
            learned,
            quizzesPassed,
            sentenceProficiency
        );
    }
}