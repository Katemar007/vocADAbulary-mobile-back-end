package com.vocadabulary.controllers;
import com.vocadabulary.auth.MockUser;
import com.vocadabulary.auth.MockUserContext;
import com.vocadabulary.models.User;
import com.vocadabulary.services.UserService;

import jakarta.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/api/users")
public class UserController {

    // uncomment this method to test the MockUserContext
    
//     @GetMapping(path = "/test-auth")
// public ResponseEntity<String> testAuth() {
//     MockUser currentUser = MockUserContext.getCurrentUser();
//     if (currentUser != null) {
//         return ResponseEntity.ok("User: " + currentUser.getId() + ", Role: " + currentUser.getRole());
//     } else {
//         return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No mock user set");
//     }
//     }

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void checkDbConnection() {
        System.out.println(">>> DB URL: " + System.getProperty("spring.datasource.url"));
    }
    
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // @GetMapping("/{id:\\d+}")
    // public ResponseEntity<User> getUser(@PathVariable Long id) {
    //     return userService.getUserById(id)
    //             .map(ResponseEntity::ok)
    //             .orElse(ResponseEntity.notFound().build());
    // }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        if (updatedUser == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}