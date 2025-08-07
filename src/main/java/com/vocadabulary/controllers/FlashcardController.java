package com.vocadabulary.controllers;

import com.vocadabulary.models.Flashcard;
import com.vocadabulary.services.FlashcardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.vocadabulary.services.UserFlashcardService;
import com.vocadabulary.models.UserFlashcard;
import com.vocadabulary.auth.MockUser;
import com.vocadabulary.auth.MockUserContext;
import com.vocadabulary.dto.FlashcardDTO;
import com.vocadabulary.dto.FlashcardRequest;
import com.vocadabulary.dto.WalletFlashcardDTO;
import com.vocadabulary.services.TtsService;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/flashcards")
public class FlashcardController {

    private final FlashcardService flashcardService;
    private final UserFlashcardService userFlashcardService;
    private final TtsService ttsService;

    public FlashcardController(FlashcardService flashcardService,
                               UserFlashcardService userFlashcardService,
                               TtsService ttsService) {
        this.flashcardService = flashcardService;
        this.userFlashcardService = userFlashcardService;
        this.ttsService = ttsService;
    }

    // ✅ Get all flashcards as DTOs (includes topicId/topicName if DTO has getters)
    @GetMapping
    public List<FlashcardDTO> getAllFlashcardDTOs() {
        return flashcardService.getAllFlashcardDTOs();
    }

    // ✅ Get one flashcard by ID (entity)
    @GetMapping("/{id}")
    public ResponseEntity<Flashcard> getFlashcardById(@PathVariable Long id) {
        Flashcard flashcard = flashcardService.getFlashcardById(id);
        return ResponseEntity.ok(flashcard);
    }

    // ✅ Add a flashcard to the user's wallet
    @PostMapping("/{id}/wallet")
    public ResponseEntity<?> addToWallet(@PathVariable Long id) {
        System.out.println("====> Entered addToWallet controller");
        flashcardService.addToWallet(id);
        return ResponseEntity.ok(Map.of("message", "Flashcard added to wallet."));
    }

    // ✅ Create a new flashcard in a topic
    @PostMapping
    public Flashcard createFlashcard(@RequestBody FlashcardRequest request) {
        if (request.getTopicId() == null) {
            throw new IllegalArgumentException("Flashcard must be assigned to a topic");
        }

        Flashcard flashcard = new Flashcard();
        flashcard.setWord(request.getWord());
        flashcard.setDefinition(request.getDefinition());
        flashcard.setExample(request.getExample());
        flashcard.setSynonyms(request.getSynonyms());
        flashcard.setPhonetic(request.getPhonetic());
        flashcard.setAudioUrl(request.getAudioUrl());

        System.out.println("=== [DEBUG] Enter createFlashcardInTopic ===");
        System.out.println("Topic ID: " + request.getTopicId());
        System.out.println("Flashcard.word (input): " + flashcard.getWord());
        System.out.println("Flashcard.definition (input): " + flashcard.getDefinition());
        System.out.println("Flashcard.example (input): " + flashcard.getExample());

        return flashcardService.createFlashcardInTopic(request.getTopicId(), flashcard);
    }

    // ✅ Update a flashcard (if user is creator or admin)
    @PutMapping("/{id}")
    public ResponseEntity<?> updateFlashcard(@PathVariable Long id, @RequestBody Flashcard updatedFlashcard) {
        try {
            Flashcard flashcard = flashcardService.updateFlashcard(id, updatedFlashcard);
            return ResponseEntity.ok(flashcard);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", e.getMessage()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Map.of("error", e.getMessage()));
        }
    }

    // ✅ Delete a flashcard (if user is creator or admin)
    @DeleteMapping("/{id}")
    public void deleteFlashcard(@PathVariable Long id) {
        Flashcard card = flashcardService.getFlashcardById(id);

        if (card.getCreatedBy() == null) {
            throw new IllegalStateException("This flashcard has no creator info. Only cards you created yourself can be deleted.");
        }
        Long mockUserId = 1L; // adjust if needed
        if (!card.getCreatedBy().equals(mockUserId)) {
            throw new IllegalStateException("You can only delete flashcards you created.");
        }

        flashcardService.deleteFlashcard(id);
    }

    // ✅ TTS audio bytes
    @GetMapping("/{id}/tts")
    public ResponseEntity<byte[]> getFlashcardTts(@PathVariable Long id) {
        Flashcard flashcard = flashcardService.getFlashcardById(id);
        if (flashcard == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Flashcard not found");
        }

        byte[] audio = ttsService.generateAudio(flashcard.getWord());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        return new ResponseEntity<>(audio, headers, HttpStatus.OK);
    }

    // ✅ How many flashcards were created by the current (mock) user
    @GetMapping("/stats/created")
    public Map<String, Long> getCreatedCountForCurrentUser() {
        MockUser current = MockUserContext.getCurrentUser();
        if (current == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No mock user");
        }
        long n = flashcardService.countCreatedByUser(current.getId());
        return Map.of("createdCount", n);
    }
}