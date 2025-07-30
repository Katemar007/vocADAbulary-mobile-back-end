package com.vocadabulary.dto;

public class UserProgressSummaryDTO {
    private long totalCards;
    private long inProgressCards;
    private long learnedCards;
    private String termComprehension;       // placeholder
    private String canUseInLanguage;        // placeholder

    public UserProgressSummaryDTO(long totalCards, long inProgressCards, long learnedCards,
                          String termComprehension, String canUseInLanguage) {
        this.totalCards = totalCards;
        this.inProgressCards = inProgressCards;
        this.learnedCards = learnedCards;
        this.termComprehension = termComprehension;
        this.canUseInLanguage = canUseInLanguage;
    }

    public long getTotalCards() {
        return totalCards;
    }

    public long getInProgressCards() {
        return inProgressCards;
    }

    public long getLearnedCards() {
        return learnedCards;
    }

    public String getTermComprehension() {
        return termComprehension;
    }

    public String getCanUseInLanguage() {
        return canUseInLanguage;
    }
}