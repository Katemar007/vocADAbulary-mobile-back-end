package com.vocadabulary.repository;

import com.vocadabulary.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //add custom query methods later if needed, like:
    // Optional<User> findByUsername(String username);
}