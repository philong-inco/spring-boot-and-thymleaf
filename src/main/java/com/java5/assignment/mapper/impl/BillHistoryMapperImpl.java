package com.java5.assignment.mapper.impl;

import com.java5.assignment.dto.request.BillHistoryCreate;
import com.java5.assignment.dto.response.BillHistoryResponse;
import com.java5.assignment.entity.BillHistory;
import com.java5.assignment.mapper.BillHistoryMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Primary
public class BillHistoryMapperImpl implements BillHistoryMapper {
    @Override
    public BillHistory createToEntity(BillHistoryCreate create) {
        BillHistory entity = BillHistory.builder()
                .note(create.getNote())
                .build();
        return entity;
    }

    @Override
    public BillHistoryResponse entityToResponse(BillHistory billHistory) {
        BillHistoryResponse response = BillHistoryResponse.builder()
                .note(billHistory.getNote())
                .staffName(billHistory.getStaff().getName())
                .billCode(billHistory.getBill().getCode())
                .id(billHistory.getId())
                .createAt(billHistory.getCreateAt() + "")
                .createBy(billHistory.getCreateBy())
                .modifyAt(billHistory.getModifyAt() + "")
                .modifyBy(billHistory.getModifyBy())
                .build();
        response.convertTime();
        return response;
    }

    @Override
    public List<BillHistoryResponse> listEntityToResponse(List<BillHistory> billHistoryList) {
        return billHistoryList.stream().map(this::entityToResponse).collect(Collectors.toList());
    }
}
