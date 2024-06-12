package com.java5.assignment.mapper.impl;

import com.java5.assignment.dto.request.ProductDetailCreate;
import com.java5.assignment.dto.request.ProductDetailUpdate;
import com.java5.assignment.dto.response.ProductDetailResponse;
import com.java5.assignment.entity.ProductDetail;
import com.java5.assignment.mapper.ProductDetailMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Primary
public class ProductDetailMapperImpl implements ProductDetailMapper {
    @Override
    public ProductDetail createToEntity(ProductDetailCreate create) {
        ProductDetail productDetail = ProductDetail.builder()
                .image(create.getImage())
                .price(new BigDecimal(create.getPrice()))
                .status(create.getStatus())
                .amount(Integer.valueOf(create.getAmount()))
                .build();
        return productDetail;
    }

    @Override
    public ProductDetail updateToEntity(ProductDetailUpdate update, ProductDetail productDetail) {
        productDetail.setAmount(Integer.valueOf(update.getAmount()));
        productDetail.setPrice(new BigDecimal(update.getPrice()));
        productDetail.setImage(update.getImage());
        productDetail.setStatus(update.getStatus());
        return productDetail;
    }

    @Override
    public ProductDetailResponse entityToResponse(ProductDetail productDetail) {
        ProductDetailResponse response = ProductDetailResponse.builder()
                .id(productDetail.getId())
                .amount(productDetail.getAmount())
                .price(productDetail.getPrice())
                .image(productDetail.getImage())
                .productName(productDetail.getProduct().getName())
                .sizeName(productDetail.getSize().getName())
                .colorName(productDetail.getColor().getName())
                .createAt(String.valueOf(productDetail.getCreateAt()))
                .createBy(productDetail.getCreateBy())
                .modifyBy(productDetail.getModifyBy())
                .modifyAt(String.valueOf(productDetail.getModifyAt()))
                .build();
        response.convertTime();
        return response;
    }

    @Override
    public Page<ProductDetailResponse> listEntityToListResponse(Page<ProductDetail> productDetailPage) {
        List<ProductDetailResponse> responses = productDetailPage.getContent()
                .stream().map(this::entityToResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(responses, productDetailPage.getPageable(), productDetailPage.getTotalElements());
    }

    @Override
    public List<ProductDetailResponse> listEntityToListResponse(List<ProductDetail> productDetailList) {
        return productDetailList.stream().map(this::entityToResponse).collect(Collectors.toList());
    }
}
