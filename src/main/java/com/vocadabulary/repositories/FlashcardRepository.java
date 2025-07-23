package com.vocadabulary.repositories;

import com.vocadabulary.models.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
    @Query("SELECT f FROM Flashcard f JOIN UserFlashcard uf ON f.id = uf.id.flashcardId WHERE uf.id.userId = :userId")
    List<Flashcard> findFlashcardsInUserWallet(@Param("userId") long userId);

    List<Flashcard> findByTopicId(Long topicId);
}