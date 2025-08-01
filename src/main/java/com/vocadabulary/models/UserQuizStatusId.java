package com.vocadabulary.models;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserQuizStatusId implements Serializable {

    private Long userId;
    private Long quizId;

    public UserQuizStatusId() {}

    public UserQuizStatusId(Long userId, Long quizId) {
        this.userId = userId;
        this.quizId = quizId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserQuizStatusId that = (UserQuizStatusId) o;
        return Objects.equals(userId, that.userId) && Objects.equals(quizId, that.quizId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, quizId);
    }
}