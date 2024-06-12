package com.java5.assignment.mapper.impl;

import com.java5.assignment.dto.request.ProductCreate;
import com.java5.assignment.dto.request.ProductUpdate;
import com.java5.assignment.dto.response.ProductResponse;
import com.java5.assignment.entity.Product;
import com.java5.assignment.mapper.ProductMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapperImpl implements ProductMapper {
    @Override
    public Product createToEntity(ProductCreate create) {
        Product product = Product.builder()
                .name(create.getName().trim())
                .description(create.getDescription().trim())
                .status(create.getStatus())
                .build();
        return product;
    }

    @Override
    public Product updateToEntity(ProductUpdate update, Product product) {
        product.setName(update.getName().trim());
        product.setDescription(update.getDescription().trim());
        product.setStatus(update.getStatus());
        return product;
    }

    @Override
    public ProductResponse entityToResponse(Product product) {
        ProductResponse response = ProductResponse.builder()
                .id(product.getId())
                .code(product.getCode())
                .name(product.getName())
                .description(product.getDescription())
                .categoryName(product.getCategory().getName())
                .status(product.getStatus())
                .createBy(product.getCreateBy())
                .createAt(String.valueOf(product.getCreateAt()))
                .modifyBy(product.getModifyBy())
                .modifyAt(String.valueOf(product.getModifyAt()))
                .build();
        response.convertTime();
        return response;
    }

    @Override
    public Page<ProductResponse> listEntityToListResponse(Page<Product> productPage) {
        List<ProductResponse> responses = productPage.getContent().stream()
                .map(this::entityToResponse).collect(Collectors.toList());
        return new PageImpl<>(responses, productPage.getPageable(), productPage.getTotalElements());
    }

    @Override
    public List<ProductResponse> listEntityToListResponse(List<Product> productList) {
        return productList.stream().map(this::entityToResponse).collect(Collectors.toList());
    }
}
