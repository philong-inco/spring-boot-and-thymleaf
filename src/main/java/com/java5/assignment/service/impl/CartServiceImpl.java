package com.java5.assignment.service.impl;

import com.java5.assignment.dto.request.CartCreate;
import com.java5.assignment.dto.request.CartUpdate;
import com.java5.assignment.dto.response.CartResponse;
import com.java5.assignment.entity.Cart;
import com.java5.assignment.entity.Customer;
import com.java5.assignment.entity.ProductDetail;
import com.java5.assignment.mapper.CartMapper;
import com.java5.assignment.repository.CartRepository;
import com.java5.assignment.repository.CustomerRepository;
import com.java5.assignment.repository.ProductDetailRepository;
import com.java5.assignment.repository.projection.CartProjection;
import com.java5.assignment.service.CartService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@Primary
public class CartServiceImpl implements CartService {
    private CartRepository cartRepository;
    private CustomerRepository customerRepository;
    private CartMapper mapper;
    private ProductDetailRepository productDetailRepository;

    public CartServiceImpl(CartRepository cartRepository, CustomerRepository customerRepository, CartMapper mapper, ProductDetailRepository productDetailRepository) {
        this.cartRepository = cartRepository;
        this.customerRepository = customerRepository;
        this.mapper = mapper;
        this.productDetailRepository = productDetailRepository;
    }

    @Override
    public void add(CartCreate create) {
        Customer customer = customerRepository.findById(create.getCustomerId()).orElse(null);
        ProductDetail productDetail = productDetailRepository.findById(create.getProductDetailId()).orElse(null);
        if (customer == null || productDetail == null)
            return;
        Cart cart = mapper.createToEntity(create);
        cart.setCustomer(customer);
        cart.setProductDetail(productDetail);
        cartRepository.save(cart);
    }

    @Override
    public void update(CartUpdate update) {
        Cart result = cartRepository.findById(update.getId()).orElse(null);
        Customer customer = customerRepository.findById(update.getCustomerId()).orElse(null);
        ProductDetail productDetail = productDetailRepository.findById(update.getProductDetailId()).orElse(null);
        if (result == null || customer == null || productDetail == null)
            return;
        Cart cart = mapper.updateToEntity(update, result);
        cart.setProductDetail(productDetail);
        cart.setCustomer(customer);
        cartRepository.save(cart);
    }

    @Override
    public void updateEntity(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public void delete(Long id) {
        cartRepository.deleteById(id);
    }

    @Override
    public List<CartProjection> getAll(Long idCustomert) {
        return cartRepository.getAll(idCustomert);
    }

    @Override
    public Cart findById(Long id) {
        return cartRepository.findById(id).orElse(null);
    }

    @Override
    public boolean existByProductDetailId(Long customerId, Long productDetailId) {
        if (cartRepository.existByProductDetailId(customerId, productDetailId).size() > 0)
            return true;
        return false;
    }

    @Override
    public CartProjection findByIdCart(Long idCart) {
        return cartRepository.findByIdCart(idCart);
    }

    @Override
    public List<CartProjection> findByListIdCart(List<Long> listIdCart) {
        List<CartProjection> listResult = new ArrayList<>();
        for (Long id : listIdCart) {
            listResult.add(findByIdCart(id));
        }
        return listResult;
    }

    @Override
    public void deleteListIdCart(List<Long> listIdCart) {
        for (Long id : listIdCart) {
            delete(id);
        }
    }

    @Override
    public Cart findByCustomerIdAndProductDetailId(Long customerId, Long productDetailId) {
        return cartRepository.findByCustomerIdAndProductDetailId(customerId, productDetailId);
    }
}
