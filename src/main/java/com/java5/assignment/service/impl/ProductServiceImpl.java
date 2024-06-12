package com.java5.assignment.service.impl;

import com.java5.assignment.common.GenerateCode;
import com.java5.assignment.dto.request.ProductCreate;
import com.java5.assignment.dto.request.ProductUpdate;
import com.java5.assignment.dto.response.ProductResponse;
import com.java5.assignment.entity.Category;
import com.java5.assignment.entity.Product;
import com.java5.assignment.mapper.ProductMapper;
import com.java5.assignment.repository.CategoryRepository;
import com.java5.assignment.repository.ProductRepository;
import com.java5.assignment.repository.projection.ProductForHomeProjecttion;
import com.java5.assignment.service.ProductService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Primary
public class ProductServiceImpl implements ProductService {
    private ProductRepository repository;
    private CategoryRepository categoryRepository;
    private GenerateCode generateCode;
    private ProductMapper mapper;

    public ProductServiceImpl(ProductRepository repository, CategoryRepository categoryRepository, GenerateCode generateCode, @Qualifier("productMapperImpl") ProductMapper mapper) {
        this.repository = repository;
        this.categoryRepository = categoryRepository;
        this.generateCode = generateCode;
        this.mapper = mapper;
    }

    @Override
    public Page<ProductResponse> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return mapper.listEntityToListResponse(repository.getAll(pageable));
    }

    @Override
    public Page<ProductResponse> findByStatus(int page, int size, int status) {
        Pageable pageable = PageRequest.of(page, size);
        return mapper.listEntityToListResponse(repository.findByStatus(pageable, status));
    }

    @Override
    public ProductResponse findById(Long id) {
        return mapper.entityToResponse(repository.findById(id).orElse(null));
    }

    @Override
    public Product findEntityById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public ProductResponse add(ProductCreate create) {
        Optional<Category> category = categoryRepository.findById(create.getIdCategory());
        if (category.isPresent()) {
            Product product = mapper.createToEntity(create);
            do {
                product.setCode(generateCode.generateCode(GenerateCode.PRODUCT_PREFIX));
            } while (repository.existsByCode(product.getCode()));
            product.setCategory(category.get());
            return mapper.entityToResponse(repository.save(product));
        }
        return null;
    }

    @Override
    public ProductResponse update(ProductUpdate update) {
        Optional<Product> product = repository.findById(update.getId());
        Optional<Category> category = categoryRepository.findById(update.getIdCategory());
        if (product.isPresent() && category.isPresent()) {
            Product toEntity = mapper.updateToEntity(update, product.get());
            toEntity.setCategory(category.get());
            return mapper.entityToResponse(repository.save(toEntity));
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<Product> product = repository.findById(id);
        if (product.isPresent())
            repository.delete(product.get());
    }

    @Override
    public boolean existByName(String name) {
        return repository.existsByName(name.trim());
    }

    @Override
    public boolean existByCode(String code) {
        return repository.existsByCode(code.trim());
    }

    @Override
    public boolean existByNameAndDifferentId(String name, Long id) {
        if (repository.existByNameAndDifferentId(name.trim(), id).size() > 0)
            return true;
        return false;
    }

    @Override
    public Page<ProductForHomeProjecttion> getProductForClient(int page, int size, String key, Long idCustomer) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.getProductForClient(idCustomer, key.trim().toLowerCase(), pageable);
    }

    @Override
    public Page<ProductForHomeProjecttion> getProductFavorite(int page, int size, Long idCustomer) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.getProductFavorite(idCustomer, pageable);
    }

    @Override
    public ProductForHomeProjecttion getProductSingleForClient(Long idCustomer, Long idProduct) {
        return repository.getProductSingleForClient(idCustomer, idProduct);
    }
}
