package com.java5.assignment.mapper;

import com.java5.assignment.dto.request.BillHistoryCreate;
import com.java5.assignment.dto.response.BillHistoryResponse;
import com.java5.assignment.entity.BillHistory;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BillHistoryMapper {

    BillHistory createToEntity(BillHistoryCreate create);

    BillHistoryResponse entityToResponse(BillHistory billHistory);

    List<BillHistoryResponse> listEntityToResponse(List<BillHistory> billHistoryList);
}
