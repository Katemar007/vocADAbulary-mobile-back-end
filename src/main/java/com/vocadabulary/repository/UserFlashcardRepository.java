package com.vocadabulary.repository;

import com.vocadabulary.model.UserFlashcard;
import com.vocadabulary.model.UserFlashcardKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserFlashcardRepository extends JpaRepository<UserFlashcard, UserFlashcardKey> {
    List<UserFlashcard> findByUserId(Long userId);
}