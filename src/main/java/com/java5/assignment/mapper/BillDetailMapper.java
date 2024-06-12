package com.java5.assignment.mapper;

import com.java5.assignment.dto.request.BillCreate;
import com.java5.assignment.dto.request.BillDetailCreate;
import com.java5.assignment.dto.request.BillDetailUpdate;
import com.java5.assignment.dto.request.BillUpdate;
import com.java5.assignment.dto.response.BillDetailResponse;
import com.java5.assignment.dto.response.BillResponse;
import com.java5.assignment.entity.Bill;
import com.java5.assignment.entity.BillDetail;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface BillDetailMapper {

    BillDetail createToEntity(BillDetailCreate create);

    BillDetail updateToEntity(BillDetailUpdate update, BillDetail bill);

    BillDetailResponse entityToResponse(BillDetail bill);

    List<BillDetailResponse> listEntityToListResponse(List<BillDetail> billDetailList);
}
