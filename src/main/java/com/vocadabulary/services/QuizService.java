package com.vocadabulary.services;

import com.vocadabulary.dto.QuizDTO;
import com.vocadabulary.models.Quiz;
import com.vocadabulary.repositories.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public List<QuizDTO> getAllQuizzes() {
        return quizRepository.findAll().stream()
                .map(quiz -> new QuizDTO(
                        quiz.getId(),
                        quiz.getTopic().getId(),
                        quiz.getQuestionText(),
                        quiz.getCorrectAnswer(),
                        quiz.getWrongAnswer1(),
                        quiz.getWrongAnswer2(),
                        quiz.getWrongAnswer3()
                ))
                .collect(Collectors.toList());
    }
}