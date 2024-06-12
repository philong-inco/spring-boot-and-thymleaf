package com.java5.assignment.repository.projection;

import com.java5.assignment.entity.Color;
import com.java5.assignment.entity.Product;
import com.java5.assignment.entity.ProductDetail;
import com.java5.assignment.entity.Size;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {ProductDetail.class, Size.class, Color.class, Product.class})
public interface ProductDetailProjection {
    @Value("#{target.product_name}")
    String getProductName();
    @Value("#{target.color_name}")
    String getColorName();
    @Value("#{target.size_name}")
    String getSizeName();
    @Value("#{target.price}")
    Float getPrice();
    @Value("#{target.amount}")
    Integer getAmount();
    @Value("#{target.status}")
    Integer getStatus();
    @Value("#{target.id}")
    Long getId();
}
