package com.vocadabulary.repositories;

import com.vocadabulary.models.SentenceTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface SentenceTemplateRepository extends JpaRepository<SentenceTemplate, Long> {

    @Query("""
        SELECT t FROM SentenceTemplate t
        JOIN SentenceTemplateBlank b ON t.id = b.templateId
        JOIN UserFlashcard uf ON uf.flashcard.id = b.targetFlashcardId
        WHERE uf.user.id = :userId AND uf.status = 'LEARNED'
        AND NOT EXISTS (
            SELECT 1
            FROM UserSentenceAttempt ua
            WHERE ua.userId = :userId
            AND ua.templateId = t.id
            AND ua.isCorrect = true
        )
        ORDER BY function('RANDOM')
    """)    
    List<SentenceTemplate> findRandomLearnedUnguessedTemplateForUser(@Param("userId") Long userId);

    @Query("""
        SELECT st FROM SentenceTemplate st 
        JOIN SentenceTemplateBlank b ON st.id = b.templateId
        WHERE b.targetFlashcardId IN :flashcardIds
    """)
    List<SentenceTemplate> findTemplatesWithFlashcardIds(@Param("flashcardIds") List<Long> flashcardIds);

}
