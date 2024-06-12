package com.java5.assignment.mapper.impl;

import com.java5.assignment.dto.request.BillDetailCreate;
import com.java5.assignment.dto.request.BillDetailUpdate;
import com.java5.assignment.dto.response.BillDetailResponse;
import com.java5.assignment.entity.BillDetail;
import com.java5.assignment.mapper.BillDetailMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Primary
public class BillDetailMapperImpl implements BillDetailMapper {
    @Override
    public BillDetail createToEntity(BillDetailCreate create) {
        BillDetail billDetail = BillDetail.builder()
                .amount(create.getAmount())
                .price(create.getPrice())
                .status(create.getStatus())
                .build();
        return billDetail;
    }

    @Override
    public BillDetail updateToEntity(BillDetailUpdate update, BillDetail bill) {
        bill.setAmount(update.getAmount());
        bill.setPrice(update.getPrice());
        bill.setStatus(update.getStatus());
        return bill;
    }

    @Override
    public BillDetailResponse entityToResponse(BillDetail bill) {
        BillDetailResponse response = BillDetailResponse.builder()
                .amount(bill.getAmount())
                .price(bill.getPrice())
                .status(bill.getStatus())
                .createAt(bill.getCreateAt() + "")
                .createBy(bill.getCreateBy())
                .modifyAt(bill.getModifyAt() + "")
                .modifyBy(bill.getModifyBy())
                .build();
        response.convertTime();
        return response;
    }

    public List<BillDetailResponse> listEntityToListResponse(List<BillDetail> billDetailList){
        return billDetailList.stream().map(this::entityToResponse).collect(Collectors.toList());
    }
}
