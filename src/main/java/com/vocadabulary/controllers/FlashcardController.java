package com.vocadabulary.controllers;

import com.vocadabulary.models.Flashcard;
import com.vocadabulary.services.FlashcardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/flashcards")
public class FlashcardController {

    private final FlashcardService flashcardService;

    public FlashcardController(FlashcardService flashcardService) {
        this.flashcardService = flashcardService;
    }

    @GetMapping
    public List<Flashcard> getAllFlashcards() {
        return flashcardService.getAllFlashcards();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Flashcard> getFlashcard(@PathVariable Long id) {
        return flashcardService.getFlashcardById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Flashcard createFlashcard(@RequestBody Flashcard flashcard) {
        return flashcardService.createFlashcard(flashcard);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Flashcard> updateFlashcard(@PathVariable Long id, @RequestBody Flashcard flashcard) {
        Flashcard updatedFlashcard = flashcardService.updateFlashcard(id, flashcard);
        if (updatedFlashcard == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updatedFlashcard);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlashcard(@PathVariable Long id) {
        flashcardService.deleteFlashcard(id);
        return ResponseEntity.noContent().build();
    }
}