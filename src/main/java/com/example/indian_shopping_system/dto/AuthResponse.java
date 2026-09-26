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
}
