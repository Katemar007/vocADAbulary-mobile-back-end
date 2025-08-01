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
import com.vocadabulary.services.UserProgressSummaryService;
import org.springframework.stereotype.Service;
import com.vocadabulary.dto.WalletFlashcardDTO;
import com.vocadabulary.services.PhoneticService;
import com.vocadabulary.services.TtsService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class FlashcardService {
    private static final Logger log = LoggerFactory.getLogger(FlashcardService.class);

    private final FlashcardRepository flashcardRepo;
    private final UserFlashcardRepository userFlashcardRepo;
    private final UserRepository userRepo;
    private final TopicService topicService;
    private final UserProgressSummaryService progressSummaryService;
    private final PhoneticService phoneticService;
    private final TtsService ttsService;

    public FlashcardService(FlashcardRepository flashcardRepo, 
                        UserFlashcardRepository userFlashcardRepo, 
                        UserRepository userRepo, TopicService topicService, 
                        UserProgressSummaryService progressSummaryService, 
                        PhoneticService phoneticService, TtsService ttsService) {
        this.flashcardRepo = flashcardRepo;
        this.userFlashcardRepo = userFlashcardRepo;
        this.userRepo = userRepo;
        this.topicService = topicService;
        this.progressSummaryService = progressSummaryService;
        this.phoneticService = phoneticService;
        this.ttsService = ttsService;
    }
    // ✅ Everyone can see all flashcards
    public List<Flashcard> getAllFlashcards() {
        return flashcardRepo.findAll();
    }
    // get active flashcards by topic ID that are not learned by the user
    public List<Flashcard> getFlashcardsByTopicId(Long topicId) {
        MockUser currentUser = MockUserContext.getCurrentUser();
        if (currentUser == null) {
            throw new IllegalStateException("Unauthorized: No mock user");
        }

        return flashcardRepo.findActiveFlashcardsByTopicId(topicId, currentUser.getId());
    }
    // ✅ Get flashcard by ID
    public Flashcard getFlashcardById(Long id) {
        Flashcard flashcard = flashcardRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Flashcard not found"));

        // Check if phonetic/audio fields are missing
        if (flashcard.getPhonetic() == null || flashcard.getPhonetic().isBlank()) {
            try {
                // Generate phonetic transcription
                String phonetic = phoneticService.generateIPA(flashcard.getWord());
                // Save to DB
                flashcard.setPhonetic(phonetic);
                flashcardRepo.save(flashcard);

                // Generate audio on the fly
                byte[] audioBytes = ttsService.generateAudio(flashcard.getWord());
                String audioBase64 = Base64.getEncoder().encodeToString(audioBytes);
                flashcard.setAudioBase64(audioBase64);

            } catch (Exception e) {
                // Log but still return the flashcard
                e.printStackTrace();
            }
        }

        return flashcard;

        }


    // ✅ Create flashcard (record creator's ID in `createdBy`)
    public Flashcard createFlashcardInTopic(Long topicId, Flashcard flashcard) {
        Topic topic = topicService.getTopicById(topicId);

        MockUser currentUser = MockUserContext.getCurrentUser();

        if (currentUser == null) {
            throw new IllegalStateException("Unauthorized: No mock user");
        }

        flashcard.setTopic(topic); // Set the topic for the flashcard
        flashcard.setCreatedBy(currentUser.getId()); // assumes Flashcard has createdBy field
        flashcard.setCreatedAt(LocalDateTime.now()); // Set creation time

        // Generate phonetic and audio right now
        try {
            String phonetic = phoneticService.generateIPA(flashcard.getWord()); //TODO: handle empty word and take the word from user input when creating a new FC
            // 2. Generate audio on the fly (not stored in DB)
            byte[] audioBytes = ttsService.generateAudio(flashcard.getWord());
            String audioBase64 = Base64.getEncoder().encodeToString(audioBytes);
            flashcard.setAudioBase64(audioBase64);
            flashcard.setPhonetic(phonetic);
        } catch (Exception e) {
            // If API fails, we still save flashcard without phonetic/audio
            e.printStackTrace();
        }
        Flashcard saved = flashcardRepo.save(flashcard);

        User user = userRepo.findById(currentUser.getId())
        .orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + currentUser.getId()));
        // Create UserFlashcard to track this flashcard for the user
        UserFlashcard userFlashcard = new UserFlashcard();
        userFlashcard.setId(new UserFlashcardId(user.getId(), saved.getId()));
        userFlashcard.setUser(user);
        userFlashcard.setFlashcard(saved);
        userFlashcard.setStatus("IN_PROGRESS");  // Default status
        userFlashcard.setInWallet(true);         // Optional: add to wallet automatically
        userFlashcard.setLastReviewed(LocalDateTime.now());

        userFlashcardRepo.save(userFlashcard);

        return saved;
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

        // Get User
        User user = userRepo.findById(currentUser.getId())
            .orElseThrow(() -> new IllegalArgumentException(
                "User not found for ID: " + currentUser.getId()));

        // Get Flashcard
        Flashcard flashcard = flashcardRepo.findById(flashcardId)
            .orElseThrow(() -> new IllegalArgumentException("Flashcard not found"));

        // Check if a UserFlashcard already exists for this user/flashcard
        Optional<UserFlashcard> existing = userFlashcardRepo.findById(
            new UserFlashcardId(currentUser.getId(), flashcardId));

        UserFlashcard userFlashcard;
        if (existing.isPresent()) {
            // If it exists, just mark it as inWallet again
            userFlashcard = existing.get();
            if (Boolean.TRUE.equals(userFlashcard.getInWallet())) {
                throw new IllegalStateException("Flashcard is already in your wallet");
            }
            userFlashcard.setInWallet(true);
            userFlashcard.setStatus("IN_PROGRESS");
        } else {
            // Otherwise, create a new record
            userFlashcard = new UserFlashcard();
            userFlashcard.setId(new UserFlashcardId(currentUser.getId(), flashcardId));
            userFlashcard.setStatus("IN_PROGRESS");
            userFlashcard.setFlashcard(flashcard);
            userFlashcard.setUser(user);
            userFlashcard.setInWallet(true);
        }

        // Always update last reviewed time
        userFlashcard.setLastReviewed(LocalDateTime.now());

        userFlashcardRepo.save(userFlashcard);
        progressSummaryService.refreshLastActive(currentUser.getId());
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

    // Record the view for progress summary OPTIONAL for the future
    public void recordFlashcardView(Long flashcardId) {
        MockUser currentUser = MockUserContext.getCurrentUser();
        if (currentUser != null) {
            progressSummaryService.refreshLastActive(currentUser.getId());
        }
    }
}