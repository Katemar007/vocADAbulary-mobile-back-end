package com.vocadabulary.services;

import com.vocadabulary.auth.MockUser;
import com.vocadabulary.auth.MockUserContext;
import com.vocadabulary.models.User;
import com.vocadabulary.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepo;

    public UserService(UserRepository userRepo) {
        this.userRepo = userRepo;
    }
// uncomment this method to test the MockUserContext - mock user has no access to all users list
// public List<User> getAllUsers() {
//     MockUser mockUser = MockUserContext.getCurrentUser();
//     if (mockUser != null) {
//         Optional<User> user = userRepo.findById(mockUser.getId());
//         return user.map(List::of).orElse(List.of());
//     }
//     return List.of(); // No users for unauthenticated requests
// }


    public List<User> getAllUsers() {
        return userRepo.findAll();
    }
// Uncomment this method to test the MockUserContext
    // public Optional<User> getUserById(Long id) {
    //     return userRepo.findById(id);
    // }

// only current mock user has access to their own record.
    public Optional<User> getCurrentMockUser() {
    MockUser mockUser = MockUserContext.getCurrentUser();

    if (mockUser != null) {
        return userRepo.findById(mockUser.getId());
    } else {
        return Optional.empty();
    }
}

    public User createUser(User user) {
        return userRepo.save(user);
    }
// Uncomment this method to test the MockUserContext
    // public User updateUser(Long id, User updatedUser) {
    //     return userRepo.findById(id).map(user -> {
    //         user.setUsername(updatedUser.getUsername());
    //         user.setEmail(updatedUser.getEmail());
    //         return userRepo.save(user);
    //     }).orElse(null);
    // }

    // only current mock user has access to update their own record
    public User updateUser(Long id, User updatedUser) {
    MockUser mockUser = MockUserContext.getCurrentUser();

    if (mockUser == null || mockUser.getId() != id) {
        throw new IllegalStateException("Unauthorized: You can only update your own account");
    }

    return userRepo.findById(id).map(user -> {
        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        return userRepo.save(user);
    }).orElse(null);
}
// Uncomment this method to test the MockUserContext
//     public void deleteUser(Long id) {
//         userRepo.deleteById(id);
//     }
// }

// only current mock user has access to delete their own record
public void deleteUser(Long id) {
    MockUser mockUser = MockUserContext.getCurrentUser();

    if (mockUser == null || mockUser.getId() != id) {
        throw new IllegalStateException("Unauthorized: You can only delete your own account");
    }

    userRepo.deleteById(id);
}
}