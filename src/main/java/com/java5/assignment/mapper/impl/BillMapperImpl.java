package com.java5.assignment.mapper.impl;

import com.java5.assignment.dto.request.BillCreate;
import com.java5.assignment.dto.request.BillUpdate;
import com.java5.assignment.dto.response.BillResponse;
import com.java5.assignment.entity.Bill;
import com.java5.assignment.mapper.BillMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class BillMapperImpl implements BillMapper {
    @Override
    public Bill createToEntity(BillCreate create) {
        Bill bill = Bill.builder()
                .note(create.getNote())
                .moneyTotal(create.getMoneyTotal())
                .productTotal(create.getProductTotal())
                .status(create.getStatus())
                .build();
        return bill;
    }

    @Override
    public Bill updateToEntity(BillUpdate update, Bill bill) {
        bill.setNote(update.getNote());
        bill.setMoneyTotal(update.getMoneyTotal());
        bill.setProductTotal(update.getProductTotal());
        bill.setStatus(update.getStatus());
        return bill;
    }

    @Override
    public BillResponse entityToResponse(Bill bill) {
        BillResponse response = BillResponse.builder()
                .note(bill.getNote())
                .code(bill.getCode())
                .customerName(bill.getCustomer().getName())
                .customerPhone(bill.getCustomer().getPhone())
//                .staffName(bill.getStaff().getName())
                .status(bill.getStatus())
                .moneyTotal(bill.getMoneyTotal())
                .productTotal(bill.getProductTotal())
                .createBy(bill.getCreateBy())
                .createAt(bill.getCreateAt() + "")
                .modifyAt(bill.getModifyAt() + "")
                .modifyBy(bill.getModifyBy())
                .id(bill.getId())
                .build();
        if (bill.getStaff() == null)
            response.setStaffName("");
        if (bill.getStaff() != null)
            response.setStaffName(bill.getStaff().getName());
        response.convertTime();
        return response;
    }
}
