package com.vocadabulary.repositories;

import com.vocadabulary.models.UserSentenceBlankStats;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSentenceBlankStatsRepository extends JpaRepository<UserSentenceBlankStats, UserSentenceBlankStats.Key> {}
