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
    ORDER BY function('RANDOM')
    LIMIT 1
""")
    SentenceTemplate findRandomLearnedTemplateForUser(@Param("userId") Long userId);

    @Query("""
        SELECT st FROM SentenceTemplate st 
        JOIN SentenceTemplateBlank b ON st.id = b.templateId
        WHERE b.targetFlashcardId IN :flashcardIds
    """)
    List<SentenceTemplate> findTemplatesWithFlashcardIds(@Param("flashcardIds") List<Long> flashcardIds);

}
