package com.java5.assignment.repository.projection;

import com.java5.assignment.entity.FavoriteList;
import com.java5.assignment.entity.Product;
import com.java5.assignment.entity.ProductDetail;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Product.class, ProductDetail.class, FavoriteList.class})
public interface ProductForHomeProjecttion {
    @Value("#{target.product_id}")
    Long getId();

    @Value("#{target.product_image}")
    String getProductImage();

    @Value("#{target.favorite}")
    Integer getFavorite();

    @Value("#{target.product_name}")
    String getProductName();

    @Value("#{target.product_description}")
    String getProductDescription();


}
