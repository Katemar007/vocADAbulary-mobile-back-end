package com.vocadabulary.services;

import com.vocadabulary.dto.QuizDetailDTO;
import com.vocadabulary.models.Quiz;
import com.vocadabulary.models.UserQuizStatus;
import com.vocadabulary.models.UserQuizStatusId;
import com.vocadabulary.repositories.QuizRepository;
import com.vocadabulary.repositories.UserQuizStatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
    private final UserQuizStatusRepository userQuizStatusRepository;

    public QuizService(QuizRepository quizRepository, UserQuizStatusRepository userQuizStatusRepository) {
        this.quizRepository = quizRepository;
        this.userQuizStatusRepository = userQuizStatusRepository;
    }

    // Fetch all quizzes for a user, including their 'isHidden' status for each quiz
    public List<QuizDetailDTO> getAllQuizzesForUser(Long userId) {
        return quizRepository.findAll().stream()
            .map(quiz -> {
                UserQuizStatusId statusId = new UserQuizStatusId(userId, quiz.getId());
                boolean isHidden = userQuizStatusRepository.findById(statusId)
                        .map(UserQuizStatus::isHidden)
                        .orElse(false); // Default to not hidden if no status found

                return new QuizDetailDTO(
                    quiz.getId(),
                    quiz.getTopic().getId(),
                    quiz.getQuestionText(),
                    quiz.getCorrectAnswer(),
                    quiz.getWrongAnswer1(),
                    quiz.getWrongAnswer2(),
                    quiz.getWrongAnswer3(),
                    isHidden
                );
            })
            .collect(Collectors.toList());
    }
}