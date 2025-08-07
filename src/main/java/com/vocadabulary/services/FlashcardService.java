package com.vocadabulary.services;

import com.vocadabulary.auth.MockUser;
import com.vocadabulary.auth.MockUserContext;
import com.vocadabulary.dto.FlashcardDTO;
import com.vocadabulary.models.Flashcard;
import com.vocadabulary.models.Topic;
import com.vocadabulary.models.User;
import com.vocadabulary.models.UserFlashcard;
import com.vocadabulary.models.UserFlashcardId;
import com.vocadabulary.repositories.FlashcardRepository;
import com.vocadabulary.repositories.UserFlashcardRepository;
import com.vocadabulary.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

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
                            UserRepository userRepo,
                            TopicService topicService,
                            UserProgressSummaryService progressSummaryService,
                            PhoneticService phoneticService,
                            TtsService ttsService) {
        this.flashcardRepo = flashcardRepo;
        this.userFlashcardRepo = userFlashcardRepo;
        this.userRepo = userRepo;
        this.topicService = topicService;
        this.progressSummaryService = progressSummaryService;
        this.phoneticService = phoneticService;
        this.ttsService = ttsService;
    }

    // ✅ Everyone can see all raw flashcards (entity)
    public List<Flashcard> getAllFlashcards() {
        return flashcardRepo.findAll();
    }

    // ✅ Get all flashcards by topic ID (DTO, no audio fetched)
    public List<FlashcardDTO> getFlashcardsByTopicId(Long topicId) {
        List<Flashcard> flashcards = flashcardRepo.findByTopicId(topicId);

        // Generate phonetics for missing entries
        for (Flashcard flashcard : flashcards) {
            try {
                if (flashcard.getPhonetic() == null || flashcard.getPhonetic().isBlank()) {
                    String phonetic = phoneticService.generateIPA(flashcard.getWord());
                    flashcard.setPhonetic(phonetic);
                    flashcardRepo.save(flashcard);
                }
            } catch (Exception e) {
                log.warn("Failed to generate phonetic for word '{}': {}", flashcard.getWord(), e.getMessage());
            }
        }

        return flashcards.stream()
                .map(f -> new FlashcardDTO(
                        f.getId(),
                        f.getWord(),
                        f.getDefinition(),
                        f.getExample(),
                        f.getSynonyms(),
                        f.getPhonetic(),
                        f.getCreatedAt(),
                        f.getCreatedBy(),
                        f.getTopic() != null ? f.getTopic().getId() : null,      // ✅ topicId
                        f.getTopic() != null ? f.getTopic().getName() : null     // ✅ topicName
                ))
                .toList();
    }

    // ✅ Get active flashcards by topic ID that are not learned by the user (raw entities)
    public List<Flashcard> getFlashcardsInProgressByTopicId(Long topicId) {
        MockUser currentUser = MockUserContext.getCurrentUser();
        if (currentUser == null) {
            throw new IllegalStateException("Unauthorized: No mock user");
        }

        List<Flashcard> flashcards = flashcardRepo.findActiveFlashcardsByTopicId(topicId, currentUser.getId());

        // Generate phonetics for missing entries
        for (Flashcard flashcard : flashcards) {
            try {
                if (flashcard.getPhonetic() == null || flashcard.getPhonetic().isBlank()) {
                    String phonetic = phoneticService.generateIPA(flashcard.getWord());
                    flashcard.setPhonetic(phonetic);
                    flashcardRepo.save(flashcard);
                }
            } catch (Exception e) {
                log.warn("Failed to generate phonetic for word '{}': {}", flashcard.getWord(), e.getMessage());
            }
        }

        // Return fresh query result (keeps method behavior as you originally had it)
        return flashcardRepo.findActiveFlashcardsByTopicId(topicId, currentUser.getId());
    }

    // ✅ Get flashcard by ID (entity); generate & attach audio base64 in-memory
    public Flashcard getFlashcardById(Long id) {
        Flashcard flashcard = flashcardRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Flashcard not found"));

        // Generate phonetic + audio if missing
        if (flashcard.getPhonetic() == null || flashcard.getPhonetic().isBlank()) {
            try {
                String phonetic = phoneticService.generateIPA(flashcard.getWord());
                flashcard.setPhonetic(phonetic);
                flashcardRepo.save(flashcard);

                byte[] audioBytes = ttsService.generateAudio(flashcard.getWord());
                String audioBase64 = Base64.getEncoder().encodeToString(audioBytes);
                flashcard.setAudioBase64(audioBase64);
            } catch (Exception e) {
                log.warn("Failed to enrich flashcard {} with phonetic/audio: {}", id, e.getMessage());
            }
        }

        return flashcard;
    }

    // ✅ Create flashcard within a topic (records createdBy & creates UserFlashcard link)
    public Flashcard createFlashcardInTopic(Long topicId, Flashcard flashcard) {
        Topic topic = topicService.getTopicById(topicId);

        MockUser currentUser = MockUserContext.getCurrentUser();
        if (currentUser == null) {
            throw new IllegalStateException("Unauthorized: No mock user");
        }

        flashcard.setTopic(topic);
        flashcard.setCreatedBy(currentUser.getId());
        flashcard.setCreatedAt(LocalDateTime.now());

        // Try to enrich with phonetic + audio (best-effort)
        try {
            String phonetic = phoneticService.generateIPA(flashcard.getWord());
            byte[] audioBytes = ttsService.generateAudio(flashcard.getWord());
            String audioBase64 = Base64.getEncoder().encodeToString(audioBytes);

            flashcard.setPhonetic(phonetic);
            flashcard.setAudioBase64(audioBase64);
        } catch (Exception e) {
            log.warn("Failed to enrich newly created flashcard '{}': {}", flashcard.getWord(), e.getMessage());
        }

        flashcard.setId(null); // ensure insert
        Flashcard saved = flashcardRepo.save(flashcard);

        User user = userRepo.findById(currentUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + currentUser.getId()));

        UserFlashcard userFlashcard = new UserFlashcard();
        userFlashcard.setId(new UserFlashcardId(user.getId(), saved.getId()));
        userFlashcard.setUser(user);
        userFlashcard.setFlashcard(saved);
        userFlashcard.setStatus("IN_PROGRESS");
        userFlashcard.setInWallet(true);
        userFlashcard.setLastReviewed(LocalDateTime.now());

        userFlashcardRepo.save(userFlashcard);

        return saved;
    }

    // ✅ Delete flashcard (admins or creator only)
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

    // ✅ Add a flashcard to the user's wallet (mock user version)
    public void addToWallet(Long flashcardId) {
        MockUser currentUser = MockUserContext.getCurrentUser();
        if (currentUser == null) {
            throw new IllegalStateException("Unauthorized: No mock user");
        }

        User user = userRepo.findById(currentUser.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found for ID: " + currentUser.getId()));

        Flashcard flashcard = flashcardRepo.findById(flashcardId)
                .orElseThrow(() -> new IllegalArgumentException("Flashcard not found"));

        Optional<UserFlashcard> existing = userFlashcardRepo.findById(
                new UserFlashcardId(currentUser.getId(), flashcardId));

        UserFlashcard userFlashcard;
        if (existing.isPresent()) {
            userFlashcard = existing.get();
            if (Boolean.TRUE.equals(userFlashcard.getInWallet())) {
                throw new IllegalStateException("Flashcard is already in your wallet");
            }
            userFlashcard.setInWallet(true);
            userFlashcard.setStatus("IN_PROGRESS");
        } else {
            userFlashcard = new UserFlashcard();
            userFlashcard.setId(new UserFlashcardId(currentUser.getId(), flashcardId));
            userFlashcard.setStatus("IN_PROGRESS");
            userFlashcard.setFlashcard(flashcard);
            userFlashcard.setUser(user);
            userFlashcard.setInWallet(true);
        }

        userFlashcard.setLastReviewed(LocalDateTime.now());
        userFlashcardRepo.save(userFlashcard);
        progressSummaryService.refreshLastActive(currentUser.getId());
    }

    // ✅ Update a flashcard (admins or creator only)
    public Flashcard updateFlashcard(Long id, Flashcard updatedFlashcard) {
        MockUser currentUser = MockUserContext.getCurrentUser();
        if (currentUser == null) {
            throw new IllegalStateException("Unauthorized: No mock user");
        }

        Flashcard flashcard = flashcardRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Flashcard not found"));

        if (!currentUser.getRole().equalsIgnoreCase("admin")
                && currentUser.getId() != flashcard.getCreatedBy()) {
            throw new IllegalStateException("You can only update your own flashcards");
        }

        flashcard.setWord(updatedFlashcard.getWord());
        flashcard.setDefinition(updatedFlashcard.getDefinition());
        flashcard.setExample(updatedFlashcard.getExample());
        flashcard.setSynonyms(updatedFlashcard.getSynonyms());
        flashcard.setPhonetic(updatedFlashcard.getPhonetic());
        flashcard.setAudioUrl(updatedFlashcard.getAudioUrl());

        return flashcardRepo.save(flashcard);
    }

    // Optional: record a view to bump last-active
    public void recordFlashcardView(Long flashcardId) {
        MockUser currentUser = MockUserContext.getCurrentUser();
        if (currentUser != null) {
            progressSummaryService.refreshLastActive(currentUser.getId());
        }
    }

    // ✅ Return all flashcards as DTOs (includes topicId/topicName)
    public List<FlashcardDTO> getAllFlashcardDTOs() {
        List<Flashcard> flashcards = flashcardRepo.findAll();
        return flashcards.stream()
                .map(f -> new FlashcardDTO(
                        f.getId(),
                        f.getWord(),
                        f.getDefinition(),
                        f.getExample(),
                        f.getSynonyms(),
                        f.getPhonetic(),
                        f.getCreatedAt(),
                        f.getCreatedBy(),
                        f.getTopic() != null ? f.getTopic().getId() : null,      // ✅ topicId
                        f.getTopic() != null ? f.getTopic().getName() : null     // ✅ topicName
                ))
                .toList();
    }

    // ✅ NEW: Count how many flashcards a given user has created (for Progress "Created")
    public long countCreatedByUser(Long userId) {
        return flashcardRepo.countByCreatedBy(userId);
    }
}