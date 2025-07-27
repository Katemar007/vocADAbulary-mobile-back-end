package com.vocadabulary.controllers;

import com.vocadabulary.dto.QuestionDTO;
import com.vocadabulary.dto.QuizDTO;
import com.vocadabulary.models.Quiz;
import com.vocadabulary.services.QuizService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/quizzes")
@CrossOrigin(origins = "*")
public class QuizController {
    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/topic/{topicId}")
    public List<QuizDTO> getQuizzesByTopic(@PathVariable Long topicId) {
        List<Quiz> quizzes = quizService.getQuizzesByTopicId(topicId);
        return quizzes.stream()
                .map(q -> new QuizDTO(
    q.getId(),
    q.getTopic().getId(),
    q.getTopic().getName(),
    q.getQuestions().stream().map(question -> new QuestionDTO(
        question.getId(),
        question.getQuestionText(),
        List.of(
            new QuestionDTO.AnswerDTO(question.getCorrectAnswer(), true),
            new QuestionDTO.AnswerDTO(question.getWrongAnswer1(), false),
            new QuestionDTO.AnswerDTO(question.getWrongAnswer2(), false),
            new QuestionDTO.AnswerDTO(question.getWrongAnswer3(), false)
        )
    )).toList()
))
                .toList();
    }

    @GetMapping("/topic/{topicId}/first")
    public QuizDTO getFirstQuizByTopic(@PathVariable Long topicId) {
        Quiz quiz = quizService.getFirstQuizByTopicId(topicId);
        if (quiz == null) return null;
        return new QuizDTO(q.getId(), q.getTopic().getId());
    }

    @GetMapping("/{id}")
    public QuizDTO getQuizById(@PathVariable Long id) {
        Quiz quiz = quizService.getQuizById(id);
        if (quiz == null) return null;

        List<QuestionDTO> questionDTOs = quiz.getQuestions().stream()
                .map(question -> new QuestionDTO(
                        question.getId(),
                        question.getQuestionText(),
                        List.of(
                                new QuestionDTO.AnswerDTO(question.getCorrectAnswer(), true),
                                new QuestionDTO.AnswerDTO(question.getWrongAnswer1(), false),
                                new QuestionDTO.AnswerDTO(question.getWrongAnswer2(), false),
                                new QuestionDTO.AnswerDTO(question.getWrongAnswer3(), false)
                        )
                ))
                .toList();

        return new QuizDTO(
                quiz.getId(),
                quiz.getTopic().getId(),
                quiz.getTopic().getName(),
                questionDTOs
        );
    }
}