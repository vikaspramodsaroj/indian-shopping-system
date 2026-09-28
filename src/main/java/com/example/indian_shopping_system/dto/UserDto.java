package com.example.indian_shopping_system.dto;

import com.example.indian_shopping_system.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String address;
    private Role role;
    private LocalDateTime createdAt;
    private long orderCount;
    private BigDecimal totalSpent;
}
