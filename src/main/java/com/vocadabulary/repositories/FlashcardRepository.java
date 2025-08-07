package com.vocadabulary.repositories;

import com.vocadabulary.models.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
    // @Query("SELECT f FROM Flashcard f JOIN UserFlashcard uf ON f.id = uf.id.flashcardId WHERE uf.id.userId = :userId")
    // List<Flashcard> findFlashcardsInUserWallet(@Param("userId") long userId);

    // Find flashcards by topic ID that are not learned by the user
    @Query("""
        SELECT f FROM Flashcard f
        WHERE f.topic.id = :topicId
        AND f.id NOT IN (
            SELECT uf.id.flashcardId FROM UserFlashcard uf
            WHERE uf.user.id = :userId AND uf.status = 'LEARNED'
        )
    """)
    List<Flashcard> findActiveFlashcardsByTopicId(@Param("topicId") Long topicId,
                                                @Param("userId") Long userId);
    
    // Find all flashcards by topic ID
    List<Flashcard> findByTopicId(Long topicId);                                            

    @Query("SELECT COUNT(f) FROM Flashcard f")
        long countAllFlashcards();

}