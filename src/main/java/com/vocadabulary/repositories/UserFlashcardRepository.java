package com.vocadabulary.repositories;

import com.vocadabulary.models.UserFlashcard;
import com.vocadabulary.models.UserFlashcardId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserFlashcardRepository extends JpaRepository<UserFlashcard, UserFlashcardId> {

       // All user_flashcards for a user
       List<UserFlashcard> findByUserId(Long userId);

       // All user_flashcards with a given status
       List<UserFlashcard> findByUserIdAndStatusAndIsHiddenFalse(Long userId, String status);

       // Find a specific user_flashcard by userId and flashcardId
       Optional<UserFlashcard> findByUserIdAndFlashcardId(Long userId, Long flashcardId);

       // Count methods (still useful)
       int countByUserIdAndStatus(Long userId, String status);
       int countByUserId(Long userId);

       // Check if a flashcard entry already exists for the user
       boolean existsByUserIdAndFlashcardId(Long userId, Long flashcardId);

       // Flashcards in the user's wallet that are not hidden
       @Query("SELECT uf FROM UserFlashcard uf JOIN FETCH uf.flashcard f " +
              "WHERE uf.user.id = :userId AND uf.inWallet = true AND uf.isHidden = false")
       List<UserFlashcard> findByUserIdAndInWalletTrueAndIsHiddenFalse(@Param("userId") Long userId);

       // // Get flashcards in wallet
       // List<UserFlashcard> findByUserIdAndInWallet(Long userId, boolean inWallet);

       // Learned flashcards (regardless of wallet)
       @Query("SELECT uf FROM UserFlashcard uf JOIN FETCH uf.flashcard f " +
              "WHERE uf.user.id = :userId AND uf.status = 'LEARNED' AND uf.isHidden = false")
       List<UserFlashcard> findLearnedAndVisibleFlashcards(@Param("userId") Long userId);

              // All learned flashcards by user ID
       @Query("SELECT uf FROM UserFlashcard uf JOIN FETCH uf.flashcard f " +
              "WHERE uf.user.id = :userId AND uf.status = 'LEARNED'")
       List<UserFlashcard> findLearnedFlashcards(@Param("userId") Long userId);

              // Learned and Hidden flashcards (regardless of wallet)
       @Query("SELECT uf FROM UserFlashcard uf JOIN FETCH uf.flashcard f " +
              "WHERE uf.user.id = :userId AND uf.status = 'LEARNED' AND uf.isHidden = true")
       List<UserFlashcard> findLearnedAndHiddenFlashcards(@Param("userId") Long userId);

       // Flashcards that are hidden
       List<UserFlashcard> findByUserIdAndIsHiddenTrue(Long userId);

       // Flashcards currently in the wallet
       // @Query("SELECT uf FROM UserFlashcard uf JOIN FETCH uf.flashcard f " +
       //        "WHERE uf.user.id = :userId AND uf.inWallet = true")
       // List<UserFlashcard> findWalletFlashcards(@Param("userId") Long userId);

       // Instead of deleting, we update inWallet=false.
       // However, if you still want a hard delete, you can keep this:
       @Transactional
       @Modifying
       void deleteByUserIdAndFlashcardId(Long userId, Long flashcardId);

       // Optional: custom update query to set inWallet = false
       @Transactional
       @Modifying
       @Query("UPDATE UserFlashcard uf SET uf.inWallet = false " +
              "WHERE uf.user.id = :userId AND uf.flashcard.id = :flashcardId")
       void setInWalletFalse(@Param("userId") Long userId,
                            @Param("flashcardId") Long flashcardId);

       // adding this for being able to delete flashcard created by user
       @Modifying
       @Transactional
       @Query("DELETE FROM UserFlashcard uf WHERE uf.flashcard.id = :flashcardId")
       void deleteByFlashcardId(@Param("flashcardId") Long flashcardId);
}