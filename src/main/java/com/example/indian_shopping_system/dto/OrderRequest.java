package com.example.indian_shopping_system.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotEmpty(message = "Cart cannot be empty")
    private List<OrderItemRequest> items;

    @NotBlank(message = "Shipping address is required")
    private String shippingAddress;

    @NotBlank(message = "Payment method is required (UPI, CARD, COD, NET_BANKING)")
    private String paymentMethod;

    private String upiId; // Optional, for UPI payments (e.g. user@okhdfcbank)
}
