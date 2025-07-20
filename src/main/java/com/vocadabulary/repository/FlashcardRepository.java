package com.vocadabulary.repository;

import com.vocadabulary.model.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
    // add custom queries here later if needed
}