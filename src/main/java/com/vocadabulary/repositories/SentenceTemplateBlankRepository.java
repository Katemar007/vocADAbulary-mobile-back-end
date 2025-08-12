package com.vocadabulary.repositories;

import com.vocadabulary.models.SentenceTemplateBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SentenceTemplateBlankRepository extends JpaRepository<SentenceTemplateBlank, Long> {
    List<SentenceTemplateBlank> findByTemplateIdOrderByBlankIndexAsc(Long templateId);

    SentenceTemplateBlank findByTemplateId(Long templateId);

    List<SentenceTemplateBlank> findByTargetFlashcardId(Long flashcardId);

    // This method is used to check if a blank exists for a specific flashcard
    boolean existsByTargetFlashcardId(Long targetFlashcardId);

}