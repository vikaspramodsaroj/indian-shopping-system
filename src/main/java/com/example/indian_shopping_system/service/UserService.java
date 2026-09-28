package com.example.indian_shopping_system.service;

import com.example.indian_shopping_system.dto.RegisterRequest;
import com.example.indian_shopping_system.dto.UserDto;
import com.example.indian_shopping_system.model.Order;
import com.example.indian_shopping_system.model.Role;
import com.example.indian_shopping_system.model.User;
import com.example.indian_shopping_system.repository.OrderRepository;
import com.example.indian_shopping_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;

    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(user -> {
            List<Order> orders = orderRepository.findByUserIdOrderByOrderDateDesc(user.getId());
            long orderCount = orders.size();
            BigDecimal totalSpent = orders.stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

            return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFullName() != null ? user.getFullName() : user.getUsername(),
                user.getPhoneNumber() != null ? user.getPhoneNumber() : "",
                user.getAddress() != null ? user.getAddress() : "",
                user.getRole(),
                user.getCreatedAt() != null ? user.getCreatedAt() : LocalDateTime.now(),
                orderCount,
                totalSpent
            );
        }).collect(Collectors.toList());
    }

    public UserDto createUser(RegisterRequest request) {
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
        user.setCreatedAt(LocalDateTime.now());

        User saved = userRepository.save(user);
        return new UserDto(
            saved.getId(),
            saved.getUsername(),
            saved.getEmail(),
            saved.getFullName(),
            saved.getPhoneNumber(),
            saved.getAddress(),
            saved.getRole(),
            saved.getCreatedAt(),
            0,
            BigDecimal.ZERO
        );
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("User not found with id " + id));
        userRepository.delete(user);
    }
}
