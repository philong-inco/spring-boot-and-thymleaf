package com.java5.assignment.mapper;

import com.java5.assignment.entity.Category;
import com.java5.assignment.dto.request.CategoryCreate;
import com.java5.assignment.dto.request.CategoryUpdate;
import com.java5.assignment.dto.response.CategoryResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CategoryMapper {
    Category createToEntity(CategoryCreate create);

    Category updateToEntity(CategoryUpdate update, Category category);

    CategoryResponse entityToResponse(Category category);

    CategoryUpdate responseToUpdate(CategoryResponse response);

    Page<CategoryResponse> listEntityToListResponse(Page<Category> categoryPages);
    List<CategoryResponse> listEntityToListResponse(List<Category> categoryPages);
}
