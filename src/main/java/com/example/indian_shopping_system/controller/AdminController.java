package com.example.indian_shopping_system.controller;

import com.example.indian_shopping_system.dto.OrderResponse;
import com.example.indian_shopping_system.dto.ProductDto;
import com.example.indian_shopping_system.model.Category;
import com.example.indian_shopping_system.model.OrderStatus;
import com.example.indian_shopping_system.service.CategoryService;
import com.example.indian_shopping_system.service.OrderService;
import com.example.indian_shopping_system.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class AdminController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final OrderService orderService;
    private final com.example.indian_shopping_system.service.UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<com.example.indian_shopping_system.dto.UserDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PostMapping("/users")
    public ResponseEntity<com.example.indian_shopping_system.dto.UserDto> createUser(
            @Valid @RequestBody com.example.indian_shopping_system.dto.RegisterRequest request) {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User with ID " + id + " has been deleted successfully.");
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto dto) {
        return ResponseEntity.ok(productService.createProduct(dto));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDto dto) {
        return ResponseEntity.ok(productService.updateProduct(id, dto));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product with ID " + id + " has been deleted successfully.");
    }

    @PostMapping("/categories")
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
        return ResponseEntity.ok(categoryService.createCategory(category));
    }

    @GetMapping("/orders")
    public ResponseEntity<List<OrderResponse>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PatchMapping("/orders/{id}/status")
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @PathVariable Long id,
            @RequestParam OrderStatus status) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id, status));
    }

    @PutMapping("/orders/{id}")
    public ResponseEntity<OrderResponse> updateOrderDetails(
            @PathVariable Long id,
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) String shippingAddress) {
        return ResponseEntity.ok(orderService.updateOrderDetails(id, status, shippingAddress));
    }
}
