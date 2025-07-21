package com.vocadabulary.repositories;

import com.vocadabulary.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // You can add custom query methods if needed
}
