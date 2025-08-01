package com.vocadabulary.services;

import com.vocadabulary.models.Quiz;
import com.vocadabulary.repositories.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    // Get all quizzes
    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    // Get quizzes by topic ID
    public List<Quiz> getQuizzesByTopic(Long topicId) {
        return quizRepository.findByTopicId(topicId);
    }
}