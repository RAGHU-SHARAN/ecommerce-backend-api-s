package com.backend.rscart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.rscart.model.CartItem;
import com.backend.rscart.model.Product;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    void deleteAllByCartId(Long id);
    List<CartItem> findByProductId(Long productId);
}
