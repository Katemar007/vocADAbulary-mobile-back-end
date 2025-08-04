package com.vocadabulary.dto;

public class UserProgressSummaryDTO {
    private long totalCards;
    private long inProgressCards;
    private long learnedCards;
    private String termComprehension;       // placeholder
    private String canUseInLanguage;        // placeholder
    private int quizzesPassed;              // <-- NEW FIELD

    public UserProgressSummaryDTO(
            long totalCards, long inProgressCards, long learnedCards,
            String termComprehension, String canUseInLanguage, int quizzesPassed // <-- add here
    ) {
        this.totalCards = totalCards;
        this.inProgressCards = inProgressCards;
        this.learnedCards = learnedCards;
        this.termComprehension = termComprehension;
        this.canUseInLanguage = canUseInLanguage;
        this.quizzesPassed = quizzesPassed; // <-- add here
    }

    public long getTotalCards() { return totalCards; }
    public long getInProgressCards() { return inProgressCards; }
    public long getLearnedCards() { return learnedCards; }
    public String getTermComprehension() { return termComprehension; }
    public String getCanUseInLanguage() { return canUseInLanguage; }
    public int getQuizzesPassed() { return quizzesPassed; } // <-- add here
}