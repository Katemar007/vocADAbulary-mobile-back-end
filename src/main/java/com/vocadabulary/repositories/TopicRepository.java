package com.vocadabulary.repositories;

import com.vocadabulary.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    // JpaRepository gives basic CRUD operations

    // Custom query to find topic name by ID
    @Query("select t.name from Topic t where t.id = :topicId")
        String findTopicName(@Param("topicId") Long topicId);


      // topic for a specific flashcard by userID
    @Query("""
        select f.topic.id from UserFlashcard uf
        join uf.flashcard f
        where uf.user.id = :userId and f.id = :flashcardId
    """)
    Long findTopicIdForUserFlashcard(@Param("userId") Long userId, @Param("flashcardId") Long flashcardId);

        // topic for a specific flashcard
    @Query("""
        select f.topic.id from UserFlashcard uf
        join uf.flashcard f
        where f.id = :flashcardId
    """)
    Long findTopicIdForFlashcard(@Param("flashcardId") Long flashcardId);

}