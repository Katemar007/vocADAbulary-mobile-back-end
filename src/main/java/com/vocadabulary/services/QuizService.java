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

    public List<Quiz> getQuizzesByTopicId(Long topicId) {
        return quizRepository.findByTopicId(topicId);
    }

    public Quiz getQuizById(Long id) {
        return quizRepository.findById(id).orElse(null);
    }

    public Quiz getFirstQuizByTopicId(Long topicId) {
        List<Quiz> quizzes = quizRepository.findByTopicId(topicId);
        return quizzes.isEmpty() ? null : quizzes.get(0);
    }
}