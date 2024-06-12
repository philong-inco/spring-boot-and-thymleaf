package com.java5.assignment.mapper.impl;

import com.java5.assignment.entity.Category;
import com.java5.assignment.mapper.CategoryMapper;
import com.java5.assignment.dto.request.CategoryCreate;
import com.java5.assignment.dto.request.CategoryUpdate;
import com.java5.assignment.dto.response.CategoryResponse;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Primary
public class CategoryMapperImpl implements CategoryMapper {
    @Override
    public Category createToEntity(CategoryCreate create) {
        if (create != null) {
            return Category.builder().name(create.getName().trim())
                    .status(create.getStatus()).build();
        }
        return null;
    }

    @Override
    public Category updateToEntity(CategoryUpdate update, Category category) {
        if (update != null && category != null) {
            category.setName(update.getName().trim());
            category.setStatus(update.getStatus());
            return category;
        }
        return null;
    }

    @Override
    public CategoryResponse entityToResponse(Category category) {
        if (category != null) {
            CategoryResponse response = CategoryResponse.builder()
                    .id(category.getId())
                    .code(category.getCode())
                    .createAt(String.valueOf(category.getCreateAt()))
                    .modifyAt(String.valueOf(category.getModifyAt()))
                    .createBy(category.getCreateBy())
                    .modifyBy(category.getModifyBy())
                    .name(category.getName())
                    .status(category.getStatus())
                    .build();
            response.convertTime();
            return response;
        }
        return null;
    }

    @Override
    public CategoryUpdate responseToUpdate(CategoryResponse response) {
        return CategoryUpdate.builder()
                .id(response.getId())
                .name(response.getName())
                .status(response.getStatus())
                .build();
    }

    @Override
    public Page<CategoryResponse> listEntityToListResponse(Page<Category> categoryPages) {
        List<CategoryResponse> responses = categoryPages.getContent()
                .stream()
                .map(this::entityToResponse)
                .collect(Collectors.toList());
        return new PageImpl<>(responses, categoryPages.getPageable(), categoryPages.getTotalElements());
    }

    @Override
    public List<CategoryResponse> listEntityToListResponse(List<Category> categoryPages) {
        return categoryPages.stream().map(this::entityToResponse).collect(Collectors.toList());
    }
}
