package com.vocadabulary.controllers;

import com.vocadabulary.dto.QuizResultDTO;
import com.vocadabulary.requests.QuizResultRequest;
import com.vocadabulary.services.QuizResultService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/quiz-results")
public class QuizResultController {

    private final QuizResultService quizResultService;

    public QuizResultController(QuizResultService quizResultService) {
        this.quizResultService = quizResultService;
    }

    @PostMapping
    public QuizResultDTO saveQuizResult(@RequestBody QuizResultRequest request) {
        return quizResultService.saveQuizResult(
                request.getQuizId(),
                request.getTotalQuestions(),
                request.getCorrectAnswers(),
                request.isPassed()
        );
    }
}