package com.vocadabulary.repositories;

import com.vocadabulary.models.UserSentenceAttempt;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSentenceAttemptRepository extends JpaRepository<UserSentenceAttempt, Long> {

}