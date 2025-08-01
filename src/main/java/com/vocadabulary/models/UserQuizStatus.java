package com.vocadabulary.models;

import jakarta.persistence.*;

@Entity
@Table(name = "user_quiz_status")
public class UserQuizStatus {

    @EmbeddedId
    private UserQuizStatusId id = new UserQuizStatusId();

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("quizId")
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    @Column(name = "is_hidden")
    private boolean isHidden = false;

    @Column(name = "successful_streak")
    private int successfulStreak = 0;

    // === Getters & Setters ===
    public UserQuizStatusId getId() {
        return id;
    }

    public void setId(UserQuizStatusId id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }

    public int getSuccessfulStreak() {
        return successfulStreak;
    }

    public void setSuccessfulStreak(int successfulStreak) {
        this.successfulStreak = successfulStreak;
    }
}
