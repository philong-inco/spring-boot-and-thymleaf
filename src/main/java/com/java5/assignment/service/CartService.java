package com.java5.assignment.service;

import com.java5.assignment.dto.request.CartCreate;
import com.java5.assignment.dto.request.CartUpdate;
import com.java5.assignment.dto.response.CartResponse;
import com.java5.assignment.entity.Cart;
import com.java5.assignment.repository.projection.CartProjection;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {
    void add(CartCreate create);

    void update(CartUpdate update);

    void updateEntity(Cart cart);

    void delete(Long id);

    List<CartProjection> getAll(Long idCustomert);

    Cart findById(Long id);

    boolean existByProductDetailId(Long customerId, Long productDetailId);

    CartProjection findByIdCart(Long idCart);

    List<CartProjection> findByListIdCart(List<Long> listIdCart);

    void deleteListIdCart(List<Long> listIdCart);

    Cart findByCustomerIdAndProductDetailId(Long customerId, Long productDetailId);
}
