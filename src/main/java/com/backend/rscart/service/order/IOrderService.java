package com.backend.rscart.service.order;

import java.util.List;

import com.backend.rscart.dto.OrderDto;
import com.backend.rscart.model.Order;

public interface IOrderService {
    Order placeOrder(Long userId);
    OrderDto getOrder(Long orderId);
    List<OrderDto> getUserOrders(Long userId);

    OrderDto convertToDto(Order order);
}
