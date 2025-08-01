package com.vocadabulary.controllers;

import com.vocadabulary.models.Flashcard;
import com.vocadabulary.services.FlashcardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.vocadabulary.services.UserFlashcardService;
import com.vocadabulary.models.UserFlashcard;

import com.vocadabulary.dto.FlashcardRequest;
import com.vocadabulary.dto.WalletFlashcardDTO;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/flashcards")
public class FlashcardController {

    private final FlashcardService flashcardService;

    private final UserFlashcardService userFlashcardService;

    public FlashcardController(FlashcardService flashcardService, UserFlashcardService userFlashcardService) {
        this.flashcardService = flashcardService;
        this.userFlashcardService = userFlashcardService;
    }

    // ✅ Get all flashcards
    @GetMapping
    public List<Flashcard> getAllFlashcards() {
        return flashcardService.getAllFlashcards();
    }

    // // ✅ Get all flashcards in user's wallet
    // @GetMapping("/wallet")
    // public List<WalletFlashcardDTO> getWalletFlashcards(
    //     @RequestParam(required = false) String status) { // Optional status filter

    // if (status != null) {
    //     return flashcardService.getWalletFlashcardsByStatus(status); //http://localhost:8080/api/flashcards/wallet?status=in_progress
    // } else {
    //     return flashcardService.getAllWalletFlashcards();
    // }
    // }

    // ✅ Add a flashcard to the user's wallet
    @PostMapping("/{id}/wallet")
    public ResponseEntity<?> addToWallet(@PathVariable Long id) {
    System.out.println("====> Entered addToWallet controller");
    flashcardService.addToWallet(id);
        return ResponseEntity.ok(Map.of("message", "Flashcard added to wallet."));
}
    // // ✅ Update a flashcard's status in the user's wallet
    // @PutMapping("/{id}/wallet")
    // public ResponseEntity<?> updateWalletFlashcardStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
    // String newStatus = request.get("status");
    // flashcardService.updateFlashcardStatusInWallet(id, newStatus);
    //     return ResponseEntity.ok(Map.of("message", "Flashcard status updated to: " + newStatus));
// }

    // // ✅ Remove a flashcard from the user's wallet
    // @DeleteMapping("/{id}/wallet")
    // public ResponseEntity<?> removeFromWallet(@PathVariable Long id) {
    //     flashcardService.removeFromWallet(id);
    //     return ResponseEntity.ok(Map.of("message", "Flashcard removed from wallet."));
    // }

    // // ✅ Get all flashcards filtered by status in user's wallet
    // @GetMapping("/wallet/{status}")
    // public List<WalletFlashcardDTO> getWalletFlashcardsByStatus(@RequestParam String status) {
    //     return flashcardService.getWalletFlashcardsByStatus(status);
    // }

    // ✅ Create a new flashcard, topic ID is required
    @PostMapping
    public Flashcard createFlashcard(@RequestBody FlashcardRequest request) {
        if (request.getTopicId() == null){
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

    System.out.println("FC controllers. Received POST to create flashcard for topic: " + request.getTopicId());
    System.out.println("FC controllersFlashcard request body: " + flashcard);

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

        // If createdBy is null -> no one can delete this card
        if (card.getCreatedBy() == null) {
            throw new IllegalStateException(
                "This flashcard has no creator info. Only cards you created yourself can be deleted."
            );
        }
        // Allow delete only if createdBy == 1 (mock user) or matches the hardcoded mock user id
        // For now, since you do not have authentication, assume mock creator id = 1
        Long mockUserId = 1L; // adjust if you have another convention

        if (!card.getCreatedBy().equals(mockUserId)) {
            throw new IllegalStateException(
                "You can only delete flashcards you created."
            );
        }

        flashcardService.deleteFlashcard(id);
    }
    // public ResponseEntity<String> deleteFlashcard(@PathVariable Long id) {
    //     try {
    //         flashcardService.deleteFlashcard(id);
    //         return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Flashcard deleted");
    //     } catch (IllegalStateException e) {
    //         return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized: You can only delete your own flashcards");
    //     } catch (IllegalArgumentException e) {
    //         return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Flashcard not found");
    //     }
    // }
}
