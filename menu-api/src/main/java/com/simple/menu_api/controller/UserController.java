package com.simple.menu_api.controller;


import com.simple.menu_api.entity.User;
import com.simple.menu_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userRepository.findAll();
            return ResponseEntity.ok(users);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        try {
            Optional<User> user = userRepository.findById(id);
            if (user.isPresent()) {
                return ResponseEntity.ok(user.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            // DEBUG: Log incoming user data
            System.out.println("=== DEBUG: User Registration ===");
            System.out.println("Received user: " + user);
            System.out.println("Username: " + user.getUsername());
            System.out.println("Email: " + user.getEmail());
            System.out.println("First: " + user.getFirst());
            System.out.println("Last: " + user.getLast());
            System.out.println("Roles: " + user.getRoles());
            System.out.println("ImageUrl: " + user.getImageUrl());
            System.out.println("ExpiryMonth: " + user.getExpiryMonth());
            System.out.println("ExpiryYear: " + user.getExpiryYear());

            // Check for existing username or email
            if (userRepository.existsByUsername(user.getUsername())) {
                System.out.println("DEBUG: Username already exists: " + user.getUsername());
                return ResponseEntity.badRequest()
                        .header("Content-Type", "application/json")
                        .body(null); // Still problematic, but at least we'll see the error
            }

            if (userRepository.existsByEmail(user.getEmail())) {
                System.out.println("DEBUG: Email already exists: " + user.getEmail());
                return ResponseEntity.badRequest()
                        .header("Content-Type", "application/json")
                        .body(null);
            }

            // Hash password before saving
            System.out.println("DEBUG: Hashing password...");
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // DEBUG: Log before saving
            System.out.println("DEBUG: About to save user: " + user);

            User savedUser = userRepository.save(user);

            // DEBUG: Log after saving
            System.out.println("DEBUG: Saved user: " + savedUser);
            System.out.println("DEBUG: Saved user ID: " + savedUser.getId());
            System.out.println("=== END DEBUG ===");

            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);

        } catch (Exception e) {
            System.err.println("DEBUG: Exception in createUser: " + e.getMessage());
            e.printStackTrace();

            // ✅ Return proper JSON error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .header("Content-Type", "application/json")
                    .body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            Optional<User> existingUser = userRepository.findById(id);
            if (existingUser.isPresent()) {
                user.setId(id);
                // Hash password if it's being updated
                if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                    user.setPassword(passwordEncoder.encode(user.getPassword()));
                } else {
                    user.setPassword(existingUser.get().getPassword());
                }
                User updatedUser = userRepository.save(user);
                return ResponseEntity.ok(updatedUser);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            if (userRepository.existsById(id)) {
                userRepository.deleteById(id);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
