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
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostConstruct
    public void checkDbConnection() {
        System.out.println(">>> DB URL: " + System.getProperty("spring.datasource.url"));
    }

    // 🔐 Login endpoint using username only
    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
        // 🔍 ADD THESE LINES:
        System.out.println("🔐 Hit /login endpoint");
        System.out.println("📨 Raw request: " + loginRequest);
        System.out.println("📨 Username: " + loginRequest.getUsername());

        String username = loginRequest.getUsername();

        Optional<User> userOpt = userService.getUserByUsername(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            MockUser mockUser = new MockUser(user.getId(), "student"); // hardcoded role
            return ResponseEntity.ok(mockUser);
        } else {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username");
        }
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        Optional<User> userOpt = userService.getUserById(id);
        return userOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
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

    // 🔧 Optional: use this to check if mock user is set properly
//    @GetMapping("/test-auth")
//    public ResponseEntity<String> testAuth() {
//        MockUser currentUser = MockUserContext.getCurrentUser();
//        if (currentUser != null) {
//            return ResponseEntity.ok("User: " + currentUser.getId() + ", Role: " + currentUser.getRole());
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No mock user set");
//        }
//    }
}