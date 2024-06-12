package com.java5.assignment.mapper;

import com.java5.assignment.dto.request.ProductCreate;
import com.java5.assignment.dto.request.ProductDetailCreate;
import com.java5.assignment.dto.request.ProductDetailUpdate;
import com.java5.assignment.dto.request.ProductUpdate;
import com.java5.assignment.dto.response.ProductDetailResponse;
import com.java5.assignment.dto.response.ProductResponse;
import com.java5.assignment.entity.Product;
import com.java5.assignment.entity.ProductDetail;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProductDetailMapper {
    ProductDetail createToEntity(ProductDetailCreate create);

    ProductDetail updateToEntity(ProductDetailUpdate update, ProductDetail productDetail);

    ProductDetailResponse entityToResponse(ProductDetail productDetail);

    Page<ProductDetailResponse> listEntityToListResponse(Page<ProductDetail> productDetailPage);

    List<ProductDetailResponse> listEntityToListResponse(List<ProductDetail> productDetailList);
}
