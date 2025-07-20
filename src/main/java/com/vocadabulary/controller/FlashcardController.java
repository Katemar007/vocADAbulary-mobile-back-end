package com.vocadabulary.controller;

import com.vocadabulary.model.Flashcard;
import com.vocadabulary.repository.FlashcardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/flashcards")
public class FlashcardController {

    @Autowired
    private FlashcardRepository flashcardRepository;

    @GetMapping
    public Iterable<Flashcard> getAllFlashcards() {
        return flashcardRepository.findAll();
    }
}