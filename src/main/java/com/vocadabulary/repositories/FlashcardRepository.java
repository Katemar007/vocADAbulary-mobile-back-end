package com.vocadabulary.repositories;

import com.vocadabulary.models.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
}