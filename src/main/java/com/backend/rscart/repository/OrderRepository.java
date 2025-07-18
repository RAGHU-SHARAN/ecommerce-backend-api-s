package com.backend.rscart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.rscart.model.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
  List<Order> findByUserId(Long userId);
}
