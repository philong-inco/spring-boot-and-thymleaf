package com.java5.assignment.service.impl;

import com.java5.assignment.common.GenerateCode;
import com.java5.assignment.dto.request.BillAddressChange;
import com.java5.assignment.dto.request.BillCreate;
import com.java5.assignment.dto.request.BillUpdate;
import com.java5.assignment.dto.response.BillResponse;
import com.java5.assignment.entity.Bill;
import com.java5.assignment.entity.Customer;
import com.java5.assignment.entity.Staff;
import com.java5.assignment.mapper.BillMapper;
import com.java5.assignment.repository.BillRepository;
import com.java5.assignment.repository.CustomerRepository;
import com.java5.assignment.repository.StaffRepository;
import com.java5.assignment.repository.projection.BillForCustomerProjection;
import com.java5.assignment.repository.projection.BillProjection;
import com.java5.assignment.service.BillService;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Primary
public class BillServiceImpl implements BillService {
    private BillRepository repository;
    private StaffRepository staffRepository;
    private CustomerRepository customerRepository;
    private GenerateCode generateCode;
    private BillMapper mapper;

    public BillServiceImpl(BillRepository repository, StaffRepository staffRepository, CustomerRepository customerRepository, GenerateCode generateCode, BillMapper mapper) {
        this.repository = repository;
        this.staffRepository = staffRepository;
        this.customerRepository = customerRepository;
        this.generateCode = generateCode;
        this.mapper = mapper;
    }

    @Override
    public BillResponse add(BillCreate create) {
        Bill bill = mapper.createToEntity(create);
        Customer customer = customerRepository.findById(create.getCustomerId()).orElse(null);
        Staff staff = null;
        if (create.getStaffId() != null) {
            staff = staffRepository.findById(create.getStaffId()).orElse(null);
        }
        if (customer == null) {
            return null;
        }
        String code = "";
        do {
            code = generateCode.generateCode(GenerateCode.BILL_PREFIX);
        } while (repository.existsByCode(code));
        bill.setCustomer(customer);
        bill.setStaff(staff);
        bill.setCode(code);
        return mapper.entityToResponse(repository.save(bill));
    }

    @Override
    public BillResponse update(BillUpdate update) {
        Bill bill = repository.findById(update.getId()).orElse(null);
        Customer customer = customerRepository.findById(update.getCustomerId()).orElse(null);
        Staff staff = staffRepository.findById(update.getStaffId()).orElse(null);
        if (bill == null || customer == null || staff == null) {
            return null;
        }
        bill = mapper.updateToEntity(update, bill);
        bill.setCustomer(customer);
        bill.setStaff(staff);
        return mapper.entityToResponse(repository.save(bill));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void changeStatus(Long id, Integer status, Staff staff) {
        Bill bill = repository.findById(id).orElse(null);
        if (bill != null && staff != null) {
            bill.setStatus(status);
            bill.setStaff(staff);
            repository.save(bill);
        }
    }

    @Override
    public Bill findEntityById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public BillResponse findResponseById(Long id) {
        return mapper.entityToResponse(repository.findById(id).orElse(null));
    }

    @Override
    public Page<BillProjection> getAllPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.getAllPage(pageable);
    }

    @Override
    public List<BillProjection> getAllList() {
        return repository.getAllList();
    }

    @Override
    public Page<BillProjection> findByStatusPage(int page, int size, Integer status) {
        Pageable pageable = PageRequest.of(page, size);
        return repository.findByStatusPage(pageable, status);
    }

    @Override
    public List<BillProjection> findByStatusList(Integer status) {
        return repository.findByStatusList(status);
    }

    @Override
    public List<BillForCustomerProjection> listBillForCustomer(Long idCustomer) {
        return repository.listBillForCustomer(idCustomer);
    }

    @Override
    public Integer countBillByStatus(Integer status) {
        return repository.countBillByStatus(status);
    }

    @Override
    public void changeStatus(Integer status, Long id) {
        Bill bill = repository.findById(id).orElse(null);
        if (bill != null) {
            bill.setStatus(status);
            repository.save(bill);
        }
    }

    @Override
    public Bill changeAddress(Long billId, BillAddressChange change) {
        Bill bill = repository.findById(billId).orElse(null);
        if (bill != null) {
            bill.setNote(change.getName() + " | " + change.getPhone() + " | " + change.getAddress());
            return repository.save(bill);
        }
        return null;
    }

    @Override
    public Bill addEntity(Bill bill) {
        return repository.save(bill);
    }

    @Override
    public boolean existByCode(String code) {
        return repository.existsByCode(code.trim());
    }
}
