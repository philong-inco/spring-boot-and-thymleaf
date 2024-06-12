package com.java5.assignment.service;

import com.java5.assignment.dto.request.BillCreate;
import com.java5.assignment.dto.request.BillDetailCreate;
import com.java5.assignment.dto.request.BillDetailUpdate;
import com.java5.assignment.dto.request.BillUpdate;
import com.java5.assignment.dto.response.BillDetailResponse;
import com.java5.assignment.dto.response.BillResponse;
import com.java5.assignment.entity.Bill;
import com.java5.assignment.entity.BillDetail;
import com.java5.assignment.repository.projection.BillDetailProjection;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BillDetailService {
    BillDetailResponse add(BillDetailCreate create);

    BillDetailResponse update(BillDetailUpdate update);

    void delete(Long id);

    void changeStatus(Long id, Integer status);

    BillDetail findEntityById(Long id);

    BillDetailResponse findResponseById(Long id);

    List<BillDetailResponse> getAllList();

    List<BillDetailProjection> getBillDetailForClientByBillId(Long billId);

    List<BillDetail> getAllBillDetailByBillId(Long billId);

    BillDetail addEntity(BillDetail billDetail);
}
