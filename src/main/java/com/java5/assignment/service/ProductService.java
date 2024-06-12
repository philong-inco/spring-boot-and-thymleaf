package com.java5.assignment.service;

import com.java5.assignment.dto.request.ProductCreate;
import com.java5.assignment.dto.request.ProductUpdate;
import com.java5.assignment.dto.response.ProductResponse;
import com.java5.assignment.entity.Product;
import com.java5.assignment.repository.ProductRepository;
import com.java5.assignment.repository.projection.ProductForHomeProjecttion;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {

    Page<ProductResponse> getAll(int page, int size);

    Page<ProductResponse> findByStatus(int page, int size, int status);

    ProductResponse findById(Long id);

    Product findEntityById(Long id);

    ProductResponse add(ProductCreate create);

    ProductResponse update(ProductUpdate update);

    void delete(Long id);

    boolean existByName(String name);

    boolean existByCode(String code);

    boolean existByNameAndDifferentId(String name, Long id);

    Page<ProductForHomeProjecttion> getProductForClient(int page, int size, String key, Long idCustomer);

    Page<ProductForHomeProjecttion> getProductFavorite(int page, int size, Long idCustomer);

    ProductForHomeProjecttion getProductSingleForClient(Long idCustomer, Long idProduct);


}
