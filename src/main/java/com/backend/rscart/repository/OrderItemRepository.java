package com.backend.rscart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.rscart.model.OrderItem;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
  List<OrderItem> findByProductId(Long id);
}
