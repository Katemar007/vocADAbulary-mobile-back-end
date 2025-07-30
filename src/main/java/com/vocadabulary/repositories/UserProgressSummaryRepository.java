package com.vocadabulary.repositories;

import com.vocadabulary.models.UserProgressSummary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface UserProgressSummaryRepository extends JpaRepository<UserProgressSummary, Long> {

    // Optional: you can add custom queries here later if needed
    @Query("SELECT COUNT(uf) FROM UserFlashcard uf WHERE uf.user.id = :userId AND uf.status = 'IN_PROGRESS'")
       long countInProgressByUser(@Param("userId") Long userId);

    @Query("SELECT COUNT(uf) FROM UserFlashcard uf WHERE uf.user.id = :userId AND uf.status = 'LEARNED'")
       long countLearnedByUser(@Param("userId") Long userId);
   
}