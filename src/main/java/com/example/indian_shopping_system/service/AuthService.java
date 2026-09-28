package com.example.indian_shopping_system.service;

import com.example.indian_shopping_system.dto.AuthResponse;
import com.example.indian_shopping_system.dto.LoginRequest;
import com.example.indian_shopping_system.dto.RegisterRequest;
import com.example.indian_shopping_system.model.Role;
import com.example.indian_shopping_system.model.User;
import com.example.indian_shopping_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new IllegalArgumentException("Username '" + request.getUsername() + "' is already taken.");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email '" + request.getEmail() + "' is already registered.");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole() != null ? request.getRole() : Role.USER);
        user.setFullName(request.getFullName() != null && !request.getFullName().isBlank() ? request.getFullName().trim() : request.getUsername());
        user.setPhoneNumber(request.getPhoneNumber() != null ? request.getPhoneNumber().trim() : "");
        user.setAddress(request.getAddress() != null ? request.getAddress().trim() : "");
        user.setCreatedAt(java.time.LocalDateTime.now());

        User savedUser = userRepository.save(user);
        return new AuthResponse(
            "User registered successfully! Welcome to Indian Shopping Portal.",
            savedUser.getUsername(),
            savedUser.getEmail(),
            savedUser.getRole(),
            savedUser.getId(),
            savedUser.getFullName(),
            savedUser.getPhoneNumber(),
            savedUser.getAddress()
        );
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
            .or(() -> userRepository.findByEmail(request.getUsername()))
            .orElseThrow(() -> new IllegalArgumentException("Invalid username or password."));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password.");
        }

        return new AuthResponse(
            "Login successful! Welcome back, " + (user.getFullName() != null ? user.getFullName() : user.getUsername()),
            user.getUsername(),
            user.getEmail(),
            user.getRole(),
            user.getId(),
            user.getFullName(),
            user.getPhoneNumber(),
            user.getAddress()
        );
    }
}

