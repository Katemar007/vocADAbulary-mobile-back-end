package com.vocadabulary.dto;

public class UserProgressSummaryDTO {
    private long totalCards;
    private long inProgressCards;
    private long learnedCards;
    private int quizzesPassed;
    private float sentenceProficiency;


    public UserProgressSummaryDTO(
            long totalCards, long inProgressCards, long learnedCards,
            int quizzesPassed, float sentenceProficiency
    ) {
        this.totalCards = totalCards;
        this.inProgressCards = inProgressCards;
        this.learnedCards = learnedCards;
        this.quizzesPassed = quizzesPassed;
        this.sentenceProficiency = sentenceProficiency;
    }

    public long getTotalCards() { return totalCards; }
    public long getInProgressCards() { return inProgressCards; }
    public long getLearnedCards() { return learnedCards; }
    public int getQuizzesPassed() { return quizzesPassed; }
    public float getSentenceProficiency() { return sentenceProficiency; }
}