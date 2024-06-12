package com.java5.assignment.mapper;

import com.java5.assignment.dto.request.BillCreate;
import com.java5.assignment.dto.request.BillUpdate;
import com.java5.assignment.dto.response.BillResponse;
import com.java5.assignment.entity.Bill;
import org.springframework.stereotype.Component;

@Component
public interface BillMapper {

    Bill createToEntity(BillCreate create);

    Bill updateToEntity(BillUpdate update, Bill bill);

    BillResponse entityToResponse(Bill bill);

}
