package com.example.indian_shopping_system.repository;

import com.example.indian_shopping_system.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryId(Long categoryId);
    List<Product> findByNameContainingIgnoreCase(String keyword);
    List<Product> findByCategoryIdAndNameContainingIgnoreCase(Long categoryId, String keyword);
}
