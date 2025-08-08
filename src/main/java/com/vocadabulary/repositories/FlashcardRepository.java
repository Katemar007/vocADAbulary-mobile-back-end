package com.vocadabulary.repositories;

import com.vocadabulary.models.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
    // @Query("SELECT f FROM Flashcard f JOIN UserFlashcard uf ON f.id = uf.id.flashcardId WHERE uf.id.userId = :userId")
    // List<Flashcard> findFlashcardsInUserWallet(@Param("userId") long userId);

    // Find flashcard by ID
    @Query("select f.word from Flashcard f where f.id = :id")
    String findWordById(@Param("id") Long id);

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

    // ✅ Count flashcards created by a specific user (for "Created" stats in Progress screen)
    long countByCreatedBy(Long createdBy);


    // ===================== NEW visibility-filtered queries =====================

    // All visible to a user (public + their own)
    @Query("""
        SELECT f FROM Flashcard f
        WHERE (f.createdBy IS NULL OR f.createdBy = :userId)
        ORDER BY f.id
    """)
    List<Flashcard> findVisibleForUser(@Param("userId") Long userId);

    // Visible to a user within a topic
    @Query("""
        SELECT f FROM Flashcard f
        WHERE f.topic.id = :topicId
          AND (f.createdBy IS NULL OR f.createdBy = :userId)
        ORDER BY f.id
    """)
    List<Flashcard> findVisibleForUserByTopic(@Param("topicId") Long topicId,
                                              @Param("userId") Long userId);

    // Single flashcard only if visible to user (optional helper)
    @Query("""
        SELECT f FROM Flashcard f
        WHERE f.id = :id
          AND (f.createdBy IS NULL OR f.createdBy = :userId)
    """)
    Optional<Flashcard> findVisibleByIdForUser(@Param("id") Long id,
                                               @Param("userId") Long userId);
}