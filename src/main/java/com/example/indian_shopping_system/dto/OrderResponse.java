package com.example.indian_shopping_system.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long orderId;
    private String orderDate;
    private BigDecimal totalAmount;
    private String status;
    private String username;
    private String userEmail;
    private String shippingAddress;
    private String paymentMethod;
    private String transactionId;
    private List<OrderItemDetail> items;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemDetail {
        private Long productId;
        private String productName;
        private Integer quantity;
        private BigDecimal price;
        private BigDecimal subtotal;
    }
}
