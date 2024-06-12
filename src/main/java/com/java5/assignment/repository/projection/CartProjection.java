package com.java5.assignment.repository.projection;

import com.java5.assignment.entity.Cart;
import com.java5.assignment.entity.Color;
import com.java5.assignment.entity.Product;
import com.java5.assignment.entity.ProductDetail;
import com.java5.assignment.entity.Size;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Cart.class, Product.class, ProductDetail.class, Size.class, Color.class})
public interface CartProjection {
    @Value("#{target.product_name}")
    String getProductName();

    @Value("#{target.size_name}")
    String getSizeName();

    @Value("#{target.color_name}")
    String getColorName();

    @Value("#{target.product_detail_price}")
    String getProductDetailPrice();

    @Value("#{target.product_detail_amount}")
    String getProductDetailAmount();

    @Value("#{target.cart_amount}")
    String getCartAmount();

    @Value("#{target.id}")
    String getId();

    @Value("#{target.product_detail_id}")
    Long getProductDetailId();


}
