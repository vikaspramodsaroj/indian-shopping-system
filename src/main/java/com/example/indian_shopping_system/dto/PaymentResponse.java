package com.example.indian_shopping_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private String transactionId;
    private String status; // SUCCESS, FAILED, PENDING
    private String paymentMethod;
    private String message;
}
