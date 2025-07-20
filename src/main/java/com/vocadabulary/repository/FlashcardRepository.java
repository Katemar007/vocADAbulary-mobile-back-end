package com.vocadabulary.repository;

import com.vocadabulary.model.Flashcard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FlashcardRepository extends JpaRepository<Flashcard, Long> {
    // add custom queries here later if needed

    // find flashcards by topic ID, this uses Spring Data JPA’s method naming to generate SQL
    List<Flashcard> findByTopicId(Long topicId);
}