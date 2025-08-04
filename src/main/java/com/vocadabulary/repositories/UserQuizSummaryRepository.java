package com.vocadabulary.repositories;

import com.vocadabulary.models.UserQuizSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserQuizSummaryRepository extends JpaRepository<UserQuizSummary, Long> {
    UserQuizSummary findByUserId(Long userId);  // Add this method!
}