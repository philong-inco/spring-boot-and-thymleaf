package com.java5.assignment.service.impl;

import com.java5.assignment.common.GenerateCode;
import com.java5.assignment.entity.Category;
import com.java5.assignment.mapper.CategoryMapper;
import com.java5.assignment.repository.CategoryRepository;
import com.java5.assignment.dto.request.CategoryCreate;
import com.java5.assignment.dto.request.CategoryUpdate;
import com.java5.assignment.dto.response.CategoryResponse;
import com.java5.assignment.service.CategoryService;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Primary
public class CategoryServiceImpl implements CategoryService {

    private CategoryMapper mapper;
    private CategoryRepository repository;
    private GenerateCode generateCode;

    public CategoryServiceImpl(CategoryMapper mapper, CategoryRepository repository, GenerateCode generateCode) {
        this.mapper = mapper;
        this.repository = repository;
        this.generateCode = generateCode;
    }

    @Override
    public Category findByName(String name) {
        return repository.findByName(name.trim());
    }

    @Override
    public CategoryResponse add(CategoryCreate categoryCreate) {
        Category category = mapper.createToEntity(categoryCreate);
        category.setCode(generateCode.generateCode(GenerateCode.CATEGORY_PREFIX));
        return mapper.entityToResponse(repository.save(category));
    }

    @Override
    public CategoryResponse update(CategoryResponse response) {
        CategoryUpdate categoryUpdate = mapper.responseToUpdate(response);
        Category category = repository.findById(categoryUpdate.getId()).orElse(null);
        if (category != null) {
            category = mapper.updateToEntity(categoryUpdate, category);
            return mapper.entityToResponse(repository.save(category));
        }
        return null;
    }

    @Override
    public List<CategoryResponse> getAll() {
        return mapper.listEntityToListResponse(repository.getAll());
    }

    @Override
    public Page<CategoryResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return mapper.listEntityToListResponse(repository.getAll(pageable));
    }

    public Page<CategoryResponse> findByStatus(int page, int size, Integer status) {
        Pageable pageable = PageRequest.of(page, size);
        return mapper.listEntityToListResponse(repository.findByStatus(pageable, status));
    }

    @Override
    public List<CategoryResponse> findByStatus(Integer status) {
        return mapper.listEntityToListResponse(repository.findByStatus(status));
    }

    @Override
    public CategoryResponse findById(Long id) {
        return mapper.entityToResponse(repository.findById(id).orElse(null));
    }

    @Override
    public boolean existByMa(String ma) {
        return repository.existsByCode(ma.trim());
    }

    @Override
    public void delete(Long id) {
        Category category = repository.findById(id).orElse(null);
        if (category != null)
            repository.delete(category);
    }

    @Override
    public boolean existByName(String name) {
        return repository.existsByName(name.trim());
    }

    @Override
    public boolean existByNameAndDifferentId(String name, Long id) {
        if (repository.existByNameAndDifferentId(name.trim(), id).size() > 0)
            return true;
        return false;
    }
}
