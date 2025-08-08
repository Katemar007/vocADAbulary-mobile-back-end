package com.vocadabulary.repositories;

import com.vocadabulary.models.UserSentenceAttemptFill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserSentenceAttemptFillRepository extends JpaRepository<UserSentenceAttemptFill, Long> {

    // total blanks submitted by this user (each fill = first submission for that attempt)
    @Query(
        value = """
            SELECT COUNT(*) 
            FROM user_sentence_attempt_fills f
            JOIN user_sentence_attempts a ON a.id = f.attempt_id
            WHERE a.user_id = :userId
        """,
        nativeQuery = true
    )
    long countTotalBlanksAttempted(@Param("userId") Long userId);

    // of those, how many were correct on that submission (i.e., first-try for that attempt)
    @Query(
        value = """
            SELECT COUNT(*) 
            FROM user_sentence_attempt_fills f
            JOIN user_sentence_attempts a ON a.id = f.attempt_id
            WHERE a.user_id = :userId AND f.is_correct = TRUE
        """,
        nativeQuery = true
    )
    long countFirstTryCorrect(@Param("userId") Long userId);

}
