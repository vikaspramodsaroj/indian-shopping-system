package com.example.indian_shopping_system.dto;

import com.example.indian_shopping_system.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String message;
    private String username;
    private String email;
    private Role role;
    private Long userId;
    private String fullName;
    private String phoneNumber;
    private String address;

    public AuthResponse(String message, String username, String email, Role role, Long userId) {
        this.message = message;
        this.username = username;
        this.email = email;
        this.role = role;
        this.userId = userId;
        this.fullName = username;
    }
}

