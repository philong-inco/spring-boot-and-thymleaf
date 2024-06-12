package com.java5.assignment.mapper;

import com.java5.assignment.dto.request.ProductCreate;
import com.java5.assignment.dto.request.ProductUpdate;
import com.java5.assignment.dto.request.RoleCreate;
import com.java5.assignment.dto.request.RoleUpdate;
import com.java5.assignment.dto.response.ProductResponse;
import com.java5.assignment.dto.response.RoleResponse;
import com.java5.assignment.entity.Product;
import com.java5.assignment.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ProductMapper {
    Product createToEntity(ProductCreate create);

    Product updateToEntity(ProductUpdate update, Product product);

    ProductResponse entityToResponse(Product product);

    Page<ProductResponse> listEntityToListResponse(Page<Product> productPage);

    List<ProductResponse> listEntityToListResponse(List<Product> productList);
}
