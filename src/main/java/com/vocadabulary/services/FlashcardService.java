package com.vocadabulary.services;

import com.vocadabulary.auth.MockUser;
import com.vocadabulary.auth.MockUserContext;
import com.vocadabulary.models.Flashcard;
import com.vocadabulary.models.User;
import com.vocadabulary.models.Topic;
import com.vocadabulary.models.UserFlashcard;
import com.vocadabulary.models.UserFlashcardId;
import com.vocadabulary.repositories.FlashcardRepository;
import com.vocadabulary.repositories.UserFlashcardRepository;
import com.vocadabulary.repositories.UserRepository;
import com.vocadabulary.services.TopicService;
import org.springframework.stereotype.Service;
import com.vocadabulary.dto.WalletFlashcardDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FlashcardService {
    private static final Logger log = LoggerFactory.getLogger(FlashcardService.class);
    private final FlashcardRepository flashcardRepo;
    private final UserFlashcardRepository userFlashcardRepo;
    private final UserRepository userRepo;
    private final TopicService topicService;

    public FlashcardService(FlashcardRepository flashcardRepo, UserFlashcardRepository userFlashcardRepo, UserRepository userRepo, TopicService topicService) {
        this.flashcardRepo = flashcardRepo;
        this.userFlashcardRepo = userFlashcardRepo;
        this.userRepo = userRepo;
        this.topicService = topicService;
    }
    // ✅ Everyone can see all flashcards
    public List<Flashcard> getAllFlashcards() {
        return flashcardRepo.findAll();
    }

    public List<Flashcard> getFlashcardsByTopicId(Long topicId) {
        return flashcardRepo.findByTopicId(topicId);
    }

    // ✅ Create flashcard (record creator's ID in `createdBy`)
    public Flashcard createFlashcardInTopic(Long topicId, Flashcard flashcard) {
        Topic topic = topicService.getTopicById(topicId);

        MockUser currentUser = MockUserContext.getCurrentUser();

        if (currentUser == null) {
            throw new IllegalStateException("Unauthorized: No mock user");
        }

        flashcard.setCreatedBy(currentUser.getId()); // assumes Flashcard has createdBy field
        return flashcardRepo.save(flashcard);
    }

    // ✅ Delete flashcard (Admins can delete anything; users can delete their own)
    public void deleteFlashcard(Long id) {
        MockUser currentUser = MockUserContext.getCurrentUser();

        if (currentUser == null) {
            throw new IllegalStateException("Unauthorized: No mock user");
        }

        Optional<Flashcard> optionalCard = flashcardRepo.findById(id);

        if (optionalCard.isEmpty()) {
            throw new IllegalArgumentException("Flashcard not found");
        }

        Flashcard card = optionalCard.get();

        if ("admin".equalsIgnoreCase(currentUser.getRole()) || currentUser.getId() == card.getCreatedBy()) {
            flashcardRepo.deleteById(id);
        } else {
            throw new IllegalStateException("Unauthorized: You can only delete your own flashcards");
        }
    }
    // ✅ Add a flashcard to user's wallet (mock user version)
    public void addToWallet(Long flashcardId) {
        MockUser currentUser = MockUserContext.getCurrentUser();

        if (currentUser == null) {
            throw new IllegalStateException("Unauthorized: No mock user");
        }

        // ✅ Get User
        User user = userRepo.findById(currentUser.getId())
            .orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + currentUser.getId()));

        // ✅ Get Flashcard from DB
        Flashcard flashcard = flashcardRepo.findById(flashcardId)
        .orElseThrow(() -> new IllegalArgumentException("Flashcard not found"));

        // Check if flashcard is already in wallet
        boolean exists = userFlashcardRepo.existsByUserIdAndFlashcardId(currentUser.getId(), flashcardId);
        if (exists) {
            throw new IllegalStateException("Flashcard is already in your wallet");
        }

        // Create UserFlashcard directly with IDs
        UserFlashcard userFlashcard = new UserFlashcard();
        userFlashcard.setId(new UserFlashcardId(currentUser.getId(), flashcardId));
        userFlashcard.setStatus("in_wallet");
        userFlashcard.setFlashcard(flashcard);
        userFlashcard.setUser(user);
        userFlashcard.setLastReviewed(LocalDateTime.now());

        userFlashcardRepo.save(userFlashcard);
    }

    // ✅ Update a flashcard's status in the user's wallet
    public void updateFlashcardStatusInWallet(Long flashcardId, String newStatus) {
        MockUser currentUser = MockUserContext.getCurrentUser();

        if (currentUser == null) {
            throw new IllegalStateException("Unauthorized: No mock user");
        }

        // ✅ Fetch the UserFlashcard
        UserFlashcardId userFlashcardId = new UserFlashcardId(currentUser.getId(), flashcardId);

        UserFlashcard userFlashcard = userFlashcardRepo.findById(userFlashcardId)
            .orElseThrow(() -> new IllegalArgumentException("Flashcard not found in wallet"));

        // ✅ Update the status
        userFlashcard.setStatus(newStatus);
        userFlashcard.setLastReviewed(LocalDateTime.now());

        userFlashcardRepo.save(userFlashcard);
    }
    // ✅ Get all flashcards in user's wallet filtered by status
    public List<WalletFlashcardDTO> getWalletFlashcardsByStatus(String status) {
        MockUser currentUser = MockUserContext.getCurrentUser();

        if (currentUser == null) {
            throw new IllegalStateException("Unauthorized: No mock user");
        }

        // Fetch UserFlashcards with status
        List<UserFlashcard> userFlashcards = userFlashcardRepo.findByUserIdAndStatus(
            currentUser.getId(), status);

        // Map to DTO
        return userFlashcards.stream()
                .map(uf -> new WalletFlashcardDTO(
                    uf.getFlashcard().getId(),
                    uf.getFlashcard().getWord(),
                    uf.getFlashcard().getDefinition(),
                    uf.getStatus(),
                    uf.getLastReviewed()
                ))
                .toList();
    }
        
    // ✅ Remove a flashcard from user's wallet
    public void removeFromWallet(Long flashcardId) {
        MockUser currentUser = MockUserContext.getCurrentUser();

        if (currentUser == null) {
            throw new IllegalStateException("Unauthorized: No mock user");
        }
            // Composite key for UserFlashcard
        UserFlashcardId userFlashcardId = new UserFlashcardId(currentUser.getId(), flashcardId);

    userFlashcardRepo.deleteById(userFlashcardId);
}

    // ✅ Get all flashcards in wallet (no status filter)
    public List<WalletFlashcardDTO> getAllWalletFlashcards() {
        MockUser currentUser = MockUserContext.getCurrentUser();

        if (currentUser == null) {
            throw new IllegalStateException("Unauthorized: No mock user");
        }

        List<UserFlashcard> userFlashcards = userFlashcardRepo.findByUserId(currentUser.getId());

        return userFlashcards.stream()
                .map(uf -> new WalletFlashcardDTO(
                        uf.getFlashcard().getId(),
                        uf.getFlashcard().getWord(),
                        uf.getFlashcard().getDefinition(),
                        uf.getStatus(),
                        uf.getLastReviewed()
                ))
                .toList();
    }

        // ✅ Update a flashcard (if user is creator or admin)
    public Flashcard updateFlashcard(Long id, Flashcard updatedFlashcard) {
        MockUser currentUser = MockUserContext.getCurrentUser();

        if (currentUser == null) {
            throw new IllegalStateException("Unauthorized: No mock user");
        }

        Flashcard flashcard = flashcardRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Flashcard not found"));

        // ⚠️ Ownership check
        if (!currentUser.getRole().equalsIgnoreCase("admin") &&
            currentUser.getId() != flashcard.getCreatedBy()) {
            throw new IllegalStateException("You can only update your own flashcards");
        }

    // Update fields
    flashcard.setWord(updatedFlashcard.getWord());
    flashcard.setDefinition(updatedFlashcard.getDefinition());
    flashcard.setExample(updatedFlashcard.getExample());
    flashcard.setSynonyms(updatedFlashcard.getSynonyms());
    flashcard.setPhonetic(updatedFlashcard.getPhonetic());
    flashcard.setAudioUrl(updatedFlashcard.getAudioUrl());

    return flashcardRepo.save(flashcard);
}
}
//  no mock user code

// package com.vocadabulary.services;

// import com.vocadabulary.models.Flashcard;
// import com.vocadabulary.repositories.FlashcardRepository;
// import org.springframework.stereotype.Service;

// import java.util.List;
// import java.util.Optional;

// @Service
// public class FlashcardService {
//     private final FlashcardRepository flashcardRepo;

//     public FlashcardService(FlashcardRepository flashcardRepo) {
//         this.flashcardRepo = flashcardRepo;
//     }

//     public List<Flashcard> getAllFlashcards() {
//         return flashcardRepo.findAll();
//     }

//     public Optional<Flashcard> getFlashcardById(Long id) {
//         return flashcardRepo.findById(id);
//     }

//     public Flashcard createFlashcard(Flashcard flashcard) {
//         return flashcardRepo.save(flashcard);
//     }

//     public Flashcard updateFlashcard(Long id, Flashcard updatedFlashcard) {
//         return flashcardRepo.findById(id).map(flashcard -> {
//             flashcard.setWord(updatedFlashcard.getWord());
//             flashcard.setDefinition(updatedFlashcard.getDefinition());
//             flashcard.setExample(updatedFlashcard.getExample());
//             flashcard.setSynonyms(updatedFlashcard.getSynonyms());
//             flashcard.setPhonetic(updatedFlashcard.getPhonetic());
//             flashcard.setAudioUrl(updatedFlashcard.getAudioUrl());
//             // Update other fields as needed
//             return flashcardRepo.save(flashcard);
//         }).orElse(null);
//     }

//     public void deleteFlashcard(Long id) {
//         flashcardRepo.deleteById(id);
//     }
// }