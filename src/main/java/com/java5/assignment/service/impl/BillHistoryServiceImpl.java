package com.java5.assignment.service.impl;

import com.java5.assignment.dto.request.BillHistoryCreate;
import com.java5.assignment.dto.response.BillHistoryResponse;
import com.java5.assignment.entity.BillHistory;
import com.java5.assignment.mapper.BillHistoryMapper;
import com.java5.assignment.repository.BillHistoryRepository;
import com.java5.assignment.repository.BillRepository;
import com.java5.assignment.repository.StaffRepository;
import com.java5.assignment.service.BillHistoryService;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Primary
public class BillHistoryServiceImpl implements BillHistoryService {
    private BillHistoryRepository repository;
    private StaffRepository staffRepository;
    private BillRepository billRepository;
    private BillHistoryMapper mapper;

    public BillHistoryServiceImpl(BillHistoryRepository repository, StaffRepository staffRepository, BillRepository billRepository, BillHistoryMapper mapper) {
        this.repository = repository;
        this.staffRepository = staffRepository;
        this.billRepository = billRepository;
        this.mapper = mapper;
    }

    @Override
    public BillHistoryResponse add(BillHistoryCreate create) {
        BillHistory bill = mapper.createToEntity(create);
        bill.setBill(billRepository.findById(create.getBillId()).orElse(null));
        bill.setStaff(staffRepository.findById(create.getStaffId()).orElse(null));
        return mapper.entityToResponse(repository.save(bill));
    }

    @Override
    public List<BillHistoryResponse> getAllByBillId(Long billId) {
        return mapper.listEntityToResponse(repository.getAllByBillId(billId));
    }
}
