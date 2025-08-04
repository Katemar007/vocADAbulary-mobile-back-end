package com.vocadabulary.services;

import com.vocadabulary.models.User;
import com.vocadabulary.models.Quiz;
import com.vocadabulary.models.UserQuizAttempt;
import com.vocadabulary.repositories.UserRepository;
import com.vocadabulary.repositories.QuizRepository;
import com.vocadabulary.repositories.UserQuizAttemptRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserQuizAttemptService {

    private final UserQuizAttemptRepository userQuizAttemptRepository;
    private final UserRepository userRepository;
    private final QuizRepository quizRepository;

    public UserQuizAttemptService(UserQuizAttemptRepository userQuizAttemptRepository,
                                  UserRepository userRepository,
                                  QuizRepository quizRepository) {
        this.userQuizAttemptRepository = userQuizAttemptRepository;
        this.userRepository = userRepository;
        this.quizRepository = quizRepository;
    }

    public UserQuizAttempt createQuizAttempt(Long userId, Long quizId, boolean isPassed) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found"));

        UserQuizAttempt attempt = new UserQuizAttempt();
        attempt.setUser(user);
        attempt.setQuiz(quiz);
        attempt.setTakenAt(LocalDateTime.now());
        attempt.setPassed(isPassed);

        return userQuizAttemptRepository.save(attempt);
    }
}