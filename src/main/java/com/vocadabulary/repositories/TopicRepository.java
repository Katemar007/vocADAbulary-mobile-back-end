package com.vocadabulary.repositories;

import com.vocadabulary.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    // JpaRepository gives basic CRUD operations
}