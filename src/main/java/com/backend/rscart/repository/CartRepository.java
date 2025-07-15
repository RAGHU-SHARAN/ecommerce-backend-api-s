package com.backend.rscart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.rscart.model.Cart;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserId(Long userId);
}
