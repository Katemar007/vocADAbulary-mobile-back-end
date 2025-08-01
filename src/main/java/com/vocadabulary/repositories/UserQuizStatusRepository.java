package com.vocadabulary.repositories;

import com.vocadabulary.models.UserQuizStatus;
import com.vocadabulary.models.UserQuizStatusId;
import com.vocadabulary.models.User;
import com.vocadabulary.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserQuizStatusRepository extends JpaRepository<UserQuizStatus, UserQuizStatusId> {
    List<UserQuizStatus> findByUser(User user);
    List<UserQuizStatus> findByQuiz(Quiz quiz);
    UserQuizStatus findByUserAndQuiz(User user, Quiz quiz);
}