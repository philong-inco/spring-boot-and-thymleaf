package com.java5.assignment.service.impl;

import com.java5.assignment.dto.request.ProductDetailCreate;
import com.java5.assignment.dto.request.ProductDetailUpdate;
import com.java5.assignment.dto.response.ProductDetailResponse;
import com.java5.assignment.entity.Color;
import com.java5.assignment.entity.Product;
import com.java5.assignment.entity.ProductDetail;
import com.java5.assignment.entity.Size;
import com.java5.assignment.mapper.ProductDetailMapper;
import com.java5.assignment.repository.ColorRepository;
import com.java5.assignment.repository.ProductDetailRepository;
import com.java5.assignment.repository.ProductRepository;
import com.java5.assignment.repository.SizeRepository;
import com.java5.assignment.repository.projection.ProductDetailProjection;
import com.java5.assignment.service.ProductDetailService;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Primary
public class ProductDetailServiceImpl implements ProductDetailService {
    private ProductDetailRepository repository;
    private ColorRepository colorRepository;
    private SizeRepository sizeRepository;

    private ProductRepository productRepository;
    private ProductDetailMapper mapper;

    public ProductDetailServiceImpl(ProductDetailRepository repository, ColorRepository colorRepository, SizeRepository sizeRepository, ProductRepository productRepository, ProductDetailMapper mapper) {
        this.repository = repository;
        this.colorRepository = colorRepository;
        this.sizeRepository = sizeRepository;
        this.productRepository = productRepository;
        this.mapper = mapper;
    }

    @Override
    public Page<ProductDetailProjection> getAll(int page, int size, Long idSP) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.getAll(pageable, idSP);
    }

    @Override
    public void add(ProductDetailCreate create) {
        Color color = colorRepository.findById(create.getIdColor()).orElse(null);
        Size size = sizeRepository.findById(create.getIdSize()).orElse(null);
        Product product = productRepository.findById(create.getIdProduct()).orElse(null);
        if (color == null || size == null || product == null) {
            return;
        }
        ProductDetail productDetail = mapper.createToEntity(create);
        productDetail.setProduct(product);
        productDetail.setSize(size);
        productDetail.setColor(color);
        repository.save(productDetail);
    }

    @Override
    public void update(ProductDetailUpdate update) {
        ProductDetail productDetail = repository.findById(update.getId()).orElse(null);
        Color color = colorRepository.findById(update.getIdColor()).orElse(null);
        Size size = sizeRepository.findById(update.getIdSize()).orElse(null);
        Product product = productRepository.findById(update.getIdProduct()).orElse(null);
        if (productDetail == null || color == null || size == null || product == null) {
            return;
        }
        productDetail = mapper.updateToEntity(update, productDetail);
        productDetail.setProduct(product);
        productDetail.setSize(size);
        productDetail.setColor(color);
        repository.save(productDetail);
    }

    @Override
    public void updateEntity(ProductDetail productDetail) {
        repository.save(productDetail);
    }

    @Override
    public void delete(Long id) {
        if (repository.findById(id).orElse(null) != null)
            repository.deleteById(id);
    }

    @Override
    public ProductDetailResponse findById(Long id) {
        ProductDetailResponse response = mapper.entityToResponse(repository.findById(id).orElse(null));
        return response;
    }

    @Override
    public boolean checkExistForCreate(Long idColor, Long idSize, Long idSP) {
        if (repository.existByColorIdAndSizeIdAndIdSP(idColor, idSize, idSP).size() > 0)
            return true;
        return false;
    }

    @Override
    public boolean checkExistForUpdate(Long idColor, Long idSize, Long idSP, Long id) {
        if (repository.existByColorIdAndSizeIdAndIdSPDifferentId(idColor, idSize, idSP, id).size() > 0)
            return true;
        return false;
    }

    @Override
    public ProductDetail findEntityById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<ProductDetailProjection> findByIdProductForClient(Long idSP) {
        return repository.findByIdProductForClient(idSP);
    }

    @Override
    public boolean upAmountById(Long idProductDetail, Integer amount) {
        ProductDetail productDetail = repository.findById(idProductDetail).orElse(null);
        if (productDetail != null) {
            productDetail.setAmount(productDetail.getAmount() + amount);
            if (repository.save(productDetail) != null)
                return true;
        }
        return false;
    }

    @Override
    public boolean downAmountById(Long idProductDetail, Integer amount) {
        ProductDetail productDetail = repository.findById(idProductDetail).orElse(null);
        if (productDetail != null) {
            productDetail.setAmount(productDetail.getAmount() - amount);
            if (repository.save(productDetail) != null)
                return true;
        }
        return false;
    }

    @Override
    public void updateProductDetailList(List<ProductDetail> list) {
        for (ProductDetail detail : list) {
            repository.save(detail);
        }
    }

    @Override
    public Page<ProductDetailProjection> findByNameOrCodeOrColorOrSize(String key, Pageable pageable) {
        return repository.findByNameOrCodeOrColorOrSize(key.trim().toLowerCase(), pageable);
    }

    @Override
    public ProductDetailProjection findProjectionById(Long idProductDetail) {
        return repository.findProjectionById(idProductDetail);
    }
}
