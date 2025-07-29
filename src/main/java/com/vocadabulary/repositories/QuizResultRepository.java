package com.vocadabulary.repositories;

import com.vocadabulary.models.QuizResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {
}