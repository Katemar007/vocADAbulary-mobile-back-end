package com.vocadabulary.repositories;

import com.vocadabulary.models.UserQuizAttempt;
import com.vocadabulary.models.User;
import com.vocadabulary.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserQuizAttemptRepository extends JpaRepository<UserQuizAttempt, Long> {
    List<UserQuizAttempt> findByUser(User user);
    List<UserQuizAttempt> findByQuiz(Quiz quiz);
    List<UserQuizAttempt> findByUserAndQuiz(User user, Quiz quiz);
}