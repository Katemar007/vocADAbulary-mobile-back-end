package com.vocadabulary.repositories;

import com.vocadabulary.models.ConstructorSentence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ConstructorSentenceRepository extends JpaRepository<ConstructorSentence, Long> {

    @Query(value = "SELECT * FROM constructor_sentences WHERE topic_id = :topicId ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<ConstructorSentence> findRandomByTopicId(Long topicId);
}
