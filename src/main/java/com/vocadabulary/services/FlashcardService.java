package com.vocadabulary.services;

import com.vocadabulary.models.Flashcard;
import com.vocadabulary.repositories.FlashcardRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlashcardService {
    private final FlashcardRepository flashcardRepo;

    public FlashcardService(FlashcardRepository flashcardRepo) {
        this.flashcardRepo = flashcardRepo;
    }

    public List<Flashcard> getAllFlashcards() {
        return flashcardRepo.findAll();
    }

    public Optional<Flashcard> getFlashcardById(Long id) {
        return flashcardRepo.findById(id);
    }

    public List<Flashcard> getFlashcardsByTopicId(Long topicId) {
        return flashcardRepo.findByTopicId(topicId);
    }

    public Flashcard createFlashcard(Flashcard flashcard) {
        return flashcardRepo.save(flashcard);
    }

    public Flashcard updateFlashcard(Long id, Flashcard updatedFlashcard) {
        return flashcardRepo.findById(id).map(flashcard -> {
            flashcard.setWord(updatedFlashcard.getWord());
            flashcard.setDefinition(updatedFlashcard.getDefinition());
            flashcard.setExample(updatedFlashcard.getExample());
            flashcard.setSynonyms(updatedFlashcard.getSynonyms());
            flashcard.setPhonetic(updatedFlashcard.getPhonetic());
            flashcard.setAudioUrl(updatedFlashcard.getAudioUrl());
            // Update other fields as needed
            return flashcardRepo.save(flashcard);
        }).orElse(null);
    }

    public void deleteFlashcard(Long id) {
        flashcardRepo.deleteById(id);
    }
}