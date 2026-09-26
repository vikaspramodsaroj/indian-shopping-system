package com.example.indian_shopping_system.service;

import com.example.indian_shopping_system.dto.*;
import com.example.indian_shopping_system.model.*;
import com.example.indian_shopping_system.repository.OrderRepository;
import com.example.indian_shopping_system.repository.ProductRepository;
import com.example.indian_shopping_system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PaymentService paymentService;

    @Transactional
    public OrderResponse placeOrder(OrderRequest request) {
        User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + request.getUserId()));

        if (request.getItems() == null || request.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order items list cannot be empty.");
        }

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(request.getShippingAddress());
        order.setPaymentMethod(request.getPaymentMethod());

        BigDecimal totalAmount = BigDecimal.ZERO;
        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequest itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + itemReq.getProductId()));

            if (product.getStockQuantity() < itemReq.getQuantity()) {
                throw new IllegalArgumentException("Insufficient stock for product '" + product.getName() + "'. Available: " + product.getStockQuantity());
            }

            // Deduct stock
            product.setStockQuantity(product.getStockQuantity() - itemReq.getQuantity());
            productRepository.save(product);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(itemReq.getQuantity());
            orderItem.setPrice(product.getPrice());

            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            totalAmount = totalAmount.add(subtotal);

            orderItems.add(orderItem);
        }

        order.setTotalAmount(totalAmount);
        order.setItems(orderItems);

        // Process payment
        PaymentRequest payReq = new PaymentRequest(null, totalAmount, request.getPaymentMethod(), request.getUpiId());
        PaymentResponse payRes = paymentService.processPayment(payReq);
        order.setTransactionId(payRes.getTransactionId());

        if ("SUCCESS".equalsIgnoreCase(payRes.getStatus())) {
            order.setStatus(OrderStatus.PAID);
        } else {
            order.setStatus(OrderStatus.PENDING);
        }

        Order saved = orderRepository.save(order);
        return mapToResponse(saved);
    }

    public List<OrderResponse> getOrdersByUser(Long userId) {
        return orderRepository.findByUserIdOrderByOrderDateDesc(userId).stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAllByOrderByOrderDateDesc().stream()
            .map(this::mapToResponse)
            .collect(Collectors.toList());
    }

    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));
        return mapToResponse(order);
    }

    @Transactional
    public OrderResponse updateOrderStatus(Long orderId, OrderStatus status) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));
        order.setStatus(status);
        return mapToResponse(orderRepository.save(order));
    }

    @Transactional
    public OrderResponse updateOrderDetails(Long orderId, OrderStatus status, String shippingAddress) {
        Order order = orderRepository.findById(orderId)
            .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));
        if (status != null) {
            order.setStatus(status);
        }
        if (shippingAddress != null && !shippingAddress.trim().isEmpty()) {
            order.setShippingAddress(shippingAddress.trim());
        }
        return mapToResponse(orderRepository.save(order));
    }

    private OrderResponse mapToResponse(Order order) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        List<OrderResponse.OrderItemDetail> itemDetails = order.getItems().stream()
            .map(item -> new OrderResponse.OrderItemDetail(
                item.getProduct().getId(),
                item.getProduct().getName(),
                item.getQuantity(),
                item.getPrice(),
                item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()))
            ))
            .collect(Collectors.toList());

        return new OrderResponse(
            order.getId(),
            order.getOrderDate().format(formatter),
            order.getTotalAmount(),
            order.getStatus().name(),
            order.getUser().getUsername(),
            order.getUser().getEmail(),
            order.getShippingAddress(),
            order.getPaymentMethod(),
            order.getTransactionId(),
            itemDetails
        );
    }
}
