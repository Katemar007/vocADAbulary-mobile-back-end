package com.vocadabulary.repositories;

import com.vocadabulary.models.UserFlashcard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.CrudRepository;
import com.vocadabulary.models.UserFlashcardId;

import java.util.List;
// import java.util.Optional;

@Repository
public interface UserFlashcardRepository extends JpaRepository<UserFlashcard, UserFlashcardId> {

    // Find all flashcards in the wallet for a user, set status "in_wallet"
    List<UserFlashcard> findByUserIdAndStatus(Long userId, String status);
    List<UserFlashcard> findByUserId(Long userId);

    // Check if a flashcard is already in user's wallet
    boolean existsByUserIdAndFlashcardId(Long userId, Long flashcardId);

    // Remove a flashcard from user's wallet
    @Transactional
    @Modifying
    void deleteByUserIdAndFlashcardId(Long userId, Long flashcardId);

    // Optional<UserFlashcard> findById(UserFlashcardId id);
}
