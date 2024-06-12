package com.java5.assignment.service;

import com.java5.assignment.dto.request.BillHistoryCreate;
import com.java5.assignment.dto.response.BillHistoryResponse;
import com.java5.assignment.entity.BillHistory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BillHistoryService {
    BillHistoryResponse add(BillHistoryCreate create);

    List<BillHistoryResponse> getAllByBillId(Long billId);
}
