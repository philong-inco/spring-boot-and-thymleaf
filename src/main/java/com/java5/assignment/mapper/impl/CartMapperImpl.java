package com.java5.assignment.mapper.impl;

import com.java5.assignment.dto.request.CartCreate;
import com.java5.assignment.dto.request.CartUpdate;
import com.java5.assignment.entity.Cart;
import com.java5.assignment.mapper.CartMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class CartMapperImpl implements CartMapper {
    @Override
    public Cart createToEntity(CartCreate create) {
        Cart cart = Cart.builder()
                .amount(create.getAmount())
                .build();
        return cart;
    }

    @Override
    public Cart updateToEntity(CartUpdate update, Cart cart) {
        cart.setAmount(cart.getAmount() + update.getAmount());
        return cart;
    }


}
