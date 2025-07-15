package com.backend.rscart.service.cart;

import java.math.BigDecimal;

import com.backend.rscart.dto.CartDto;
import com.backend.rscart.model.Cart;
import com.backend.rscart.model.User;

public interface ICartService {
    Cart getCart(Long id);
    void clearCart(Long id);
    BigDecimal getTotalPrice(Long id);

    Cart initializeNewCart(User user);

    Cart getCartByUserId(Long userId);

    CartDto convertToDto(Cart cart);
}
