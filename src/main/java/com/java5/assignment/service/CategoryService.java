package com.java5.assignment.service;

import com.java5.assignment.entity.Category;
import com.java5.assignment.dto.request.CategoryCreate;
import com.java5.assignment.dto.request.CategoryUpdate;
import com.java5.assignment.dto.response.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CategoryService {
    Category findByName(String name);

    CategoryResponse add(CategoryCreate categoryCreate);

    CategoryResponse update(CategoryResponse categoryResponse);

    List<CategoryResponse> getAll();
    Page<CategoryResponse> getAll(int page, int size);

    Page<CategoryResponse> findByStatus(int page, int size, Integer status);
    List<CategoryResponse> findByStatus(Integer status);

    CategoryResponse findById(Long id);

    boolean existByMa(String ma);

    void delete(Long id);

    boolean existByName(String name);

    boolean existByNameAndDifferentId(String name, Long id);

}
