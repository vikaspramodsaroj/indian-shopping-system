package com.example.indian_shopping_system.controller;

import com.example.indian_shopping_system.dto.ProductDto;
import com.example.indian_shopping_system.model.Category;
import com.example.indian_shopping_system.service.CategoryService;
import com.example.indian_shopping_system.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getProducts(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String search) {

        if (categoryId != null) {
            return ResponseEntity.ok(productService.getProductsByCategory(categoryId));
        } else if (search != null && !search.trim().isEmpty()) {
            return ResponseEntity.ok(productService.searchProducts(search.trim()));
        }
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @GetMapping("/categories")
    public ResponseEntity<List<Category>> getCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
}
