package com.vocadabulary.controllers;

import com.vocadabulary.models.Flashcard;
import com.vocadabulary.services.FlashcardService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vocadabulary.dto.FlashcardRequest;
import com.vocadabulary.dto.WalletFlashcardDTO;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/flashcards")
public class FlashcardController {

    private final FlashcardService flashcardService;

    public FlashcardController(FlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    // ✅ Get all flashcards
    @GetMapping
    public List<Flashcard> getAllFlashcards() {
        return flashcardService.getAllFlashcards();
    }

    // ✅ Get all flashcards in user's wallet
    @GetMapping("/wallet")
    public List<WalletFlashcardDTO> getWalletFlashcards(
        @RequestParam(required = false) String status) { // Optional status filter

    if (status != null) {
        return flashcardService.getWalletFlashcardsByStatus(status); //http://localhost:8080/api/flashcards/wallet?status=in_progress
    } else {
        return flashcardService.getAllWalletFlashcards();
    }
    }

    // ✅ Add a flashcard to the user's wallet
    @PostMapping("/{id}/wallet")
    public ResponseEntity<?> addToWallet(@PathVariable Long id) {
    System.out.println("====> Entered addToWallet controller");
    flashcardService.addToWallet(id);
        return ResponseEntity.ok(Map.of("message", "Flashcard added to wallet."));
}
    // ✅ Update a flashcard's status in the user's wallet
    @PutMapping("/{id}/wallet")
    public ResponseEntity<?> updateWalletFlashcardStatus(@PathVariable Long id, @RequestBody Map<String, String> request) {
    String newStatus = request.get("status");
    flashcardService.updateFlashcardStatusInWallet(id, newStatus);
        return ResponseEntity.ok(Map.of("message", "Flashcard status updated to: " + newStatus));
}

    // ✅ Remove a flashcard from the user's wallet
    @DeleteMapping("/{id}/wallet")
    public ResponseEntity<?> removeFromWallet(@PathVariable Long id) {
        flashcardService.removeFromWallet(id);
        return ResponseEntity.ok(Map.of("message", "Flashcard removed from wallet."));
    }

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
    public ResponseEntity<String> deleteFlashcard(@PathVariable Long id) {
        flashcardService.deleteFlashcard(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Flashcard deleted");
    }
}



//  no mock user code

// @RestController
// @RequestMapping("/api/flashcards")
// public class FlashcardController {

//     private final FlashcardService flashcardService;

//     public FlashcardController(FlashcardService flashcardService) {
//         this.flashcardService = flashcardService;
//     }

//     @GetMapping
//     public List<Flashcard> getAllFlashcards() {
//         return flashcardService.getAllFlashcards();
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<Flashcard> getFlashcard(@PathVariable Long id) {
//         return flashcardService.getFlashcardById(id)
//                 .map(ResponseEntity::ok)
//                 .orElse(ResponseEntity.notFound().build());
//     }

//     @PostMapping
//     public Flashcard createFlashcard(@RequestBody Flashcard flashcard) {
//         return flashcardService.createFlashcard(flashcard);
//     }

//     @PutMapping("/{id}")
//     public ResponseEntity<Flashcard> updateFlashcard(@PathVariable Long id, @RequestBody Flashcard flashcard) {
//         Flashcard updatedFlashcard = flashcardService.updateFlashcard(id, flashcard);
//         if (updatedFlashcard == null) return ResponseEntity.notFound().build();
//         return ResponseEntity.ok(updatedFlashcard);
//     }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<Void> deleteFlashcard(@PathVariable Long id) {
//         flashcardService.deleteFlashcard(id);
//         return ResponseEntity.noContent().build();
//     }
// }