package com.vocadabulary.services;

import com.vocadabulary.models.UserFlashcard;
import com.vocadabulary.repositories.UserFlashcardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserFlashcardService {
    private final UserFlashcardRepository userFlashcardRepo;

    public UserFlashcardService(UserFlashcardRepository userFlashcardRepo) {
        this.userFlashcardRepo = userFlashcardRepo;
    }

    public List<UserFlashcard> getAllByUserId(Long userId) {
        return userFlashcardRepo.findByUserId(userId);
    }
}