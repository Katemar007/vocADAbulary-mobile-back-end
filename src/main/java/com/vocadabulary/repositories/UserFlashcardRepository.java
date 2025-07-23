package com.vocadabulary.repositories;

import com.vocadabulary.models.UserFlashcard;
import com.vocadabulary.models.UserFlashcardId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFlashcardRepository extends JpaRepository<UserFlashcard, UserFlashcardId> {
    List<UserFlashcard> findByUserId(Long userId);
    List<UserFlashcard> findByUserIdAndStatus(Long userId, String status);
}