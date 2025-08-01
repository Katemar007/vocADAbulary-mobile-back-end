package com.vocadabulary.repositories;

import com.vocadabulary.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    // Get quizzes by topic ID
    List<Quiz> findByTopicId(Long topicId);
}