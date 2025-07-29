package com.vocadabulary.services;

import com.vocadabulary.dto.QuizResultDTO;
import com.vocadabulary.models.Quiz;
import com.vocadabulary.models.QuizResult;
import com.vocadabulary.models.User;
import com.vocadabulary.repositories.QuizRepository;
import com.vocadabulary.repositories.QuizResultRepository;
import com.vocadabulary.repositories.UserRepository;
import com.vocadabulary.auth.MockUserContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class QuizResultService {

    private final QuizResultRepository quizResultRepository;
    private final QuizRepository quizRepository;
    private final UserRepository userRepository;

    public QuizResultService(QuizResultRepository quizResultRepository,
                             QuizRepository quizRepository,
                             UserRepository userRepository) {
        this.quizResultRepository = quizResultRepository;
        this.quizRepository = quizRepository;
        this.userRepository = userRepository;
    }

    public QuizResultDTO saveQuizResult(Long quizId, int totalQuestions, int correctAnswers, boolean passed) {
        Long userId = MockUserContext.getCurrentUser().getId();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + quizId));

        QuizResult result = new QuizResult();
        result.setUser(user);
        result.setQuiz(quiz);
        result.setTotalQuestions(totalQuestions);
        result.setCorrectAnswers(correctAnswers);
        result.setPassed(passed);
        result.setTakenAt(LocalDateTime.now());

        QuizResult savedResult = quizResultRepository.save(result);

        return new QuizResultDTO(
                savedResult.getId(),
                quiz.getId(),
                user.getId(),
                savedResult.getTotalQuestions(),
                savedResult.getCorrectAnswers(),
                savedResult.isPassed(),
                savedResult.getTakenAt()
        );
    }
}