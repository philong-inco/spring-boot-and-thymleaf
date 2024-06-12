package com.java5.assignment.service.impl;

import com.java5.assignment.dto.request.BillDetailCreate;
import com.java5.assignment.dto.request.BillDetailUpdate;
import com.java5.assignment.dto.response.BillDetailResponse;
import com.java5.assignment.entity.Bill;
import com.java5.assignment.entity.BillDetail;
import com.java5.assignment.entity.ProductDetail;
import com.java5.assignment.mapper.BillDetailMapper;
import com.java5.assignment.repository.BillDetailRepository;
import com.java5.assignment.repository.BillRepository;
import com.java5.assignment.repository.ProductDetailRepository;
import com.java5.assignment.repository.projection.BillDetailProjection;
import com.java5.assignment.service.BillDetailService;
import com.java5.assignment.service.BillService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Primary
public class BillDetailServiceImpl implements BillDetailService {
    private BillDetailRepository repository;
    private BillRepository billRepository;
    private ProductDetailRepository productDetailRepository;
    private BillDetailMapper mapper;


    public BillDetailServiceImpl(BillDetailRepository repository, BillRepository billRepository, ProductDetailRepository productDetailRepository, BillDetailMapper mapper) {
        this.repository = repository;
        this.billRepository = billRepository;
        this.productDetailRepository = productDetailRepository;
        this.mapper = mapper;
    }

    @Override
    public BillDetailResponse add(BillDetailCreate create) {
        Bill bill = billRepository.findById(create.getBillId()).orElse(null);
        ProductDetail productDetail = productDetailRepository.findById(create.getProductDetailId()).orElse(null);
        if (bill == null || productDetail == null)
            return null;
        BillDetail billDetail = mapper.createToEntity(create);
        billDetail.setBill(bill);
        billDetail.setProductDetail(productDetail);
        return mapper.entityToResponse(repository.save(billDetail));
    }

    @Override
    public BillDetailResponse update(BillDetailUpdate update) {
        BillDetail billDetail = repository.findById(update.getBillId()).orElse(null);
        Bill bill = billRepository.findById(update.getBillId()).orElse(null);
        ProductDetail productDetail = productDetailRepository.findById(update.getProductDetailId()).orElse(null);
        if (bill == null || productDetail == null || billDetail == null)
            return null;
        billDetail = mapper.updateToEntity(update, billDetail);
        billDetail.setProductDetail(productDetail);
        billDetail.setBill(bill);
        return mapper.entityToResponse(repository.save(billDetail));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void changeStatus(Long id, Integer status) {
        BillDetail billDetail = repository.findById(id).orElse(null);
        if (billDetail == null) return;
        billDetail.setStatus(status);
        repository.save(billDetail);
    }

    @Override
    public BillDetail findEntityById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public BillDetailResponse findResponseById(Long id) {
        BillDetail billDetail = repository.findById(id).orElse(null);
        if (billDetail != null)
            return mapper.entityToResponse(billDetail);
        return null;
    }

    @Override
    public List<BillDetailResponse> getAllList() {
        return mapper.listEntityToListResponse(repository.findAll());
    }

    @Override
    public List<BillDetailProjection> getBillDetailForClientByBillId(Long billId) {
        return repository.getBillDetailForClientByBillId(billId);
    }

    @Override
    public List<BillDetail> getAllBillDetailByBillId(Long billId) {
        return repository.getBillDetailByBillId(billId);
    }

    @Override
    public BillDetail addEntity(BillDetail billDetail) {
        return repository.save(billDetail);
    }
}
