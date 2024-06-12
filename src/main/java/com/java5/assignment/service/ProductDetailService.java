package com.java5.assignment.service;

import com.java5.assignment.dto.request.ProductDetailCreate;
import com.java5.assignment.dto.request.ProductDetailUpdate;
import com.java5.assignment.dto.response.ProductDetailResponse;
import com.java5.assignment.entity.ProductDetail;
import com.java5.assignment.repository.projection.ProductDetailProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductDetailService {
    Page<ProductDetailProjection> getAll(int page, int size, Long idSP);

    void add(ProductDetailCreate create);

    void update(ProductDetailUpdate update);

    void updateEntity(ProductDetail productDetail);

    void delete(Long id);

    ProductDetailResponse findById(Long id);

    boolean checkExistForCreate(Long idColor, Long idSize, Long idSP);

    boolean checkExistForUpdate(Long idColor, Long idSize, Long idSP, Long id);

    ProductDetail findEntityById(Long id);

    List<ProductDetailProjection> findByIdProductForClient(Long idSP);

    boolean upAmountById(Long idProductDetail, Integer amount);

    boolean downAmountById(Long idProductDetail, Integer amount);

    void updateProductDetailList(List<ProductDetail> list);

    Page<ProductDetailProjection> findByNameOrCodeOrColorOrSize(String key, Pageable pageable);

    ProductDetailProjection findProjectionById(Long idProductDetail);
}
