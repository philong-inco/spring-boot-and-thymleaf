package com.java5.assignment.service;

import com.java5.assignment.dto.request.BillAddressChange;
import com.java5.assignment.dto.request.BillCreate;
import com.java5.assignment.dto.request.BillUpdate;
import com.java5.assignment.dto.response.BillResponse;
import com.java5.assignment.entity.Bill;
import com.java5.assignment.entity.Staff;
import com.java5.assignment.repository.projection.BillForCustomerProjection;
import com.java5.assignment.repository.projection.BillProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BillService {

    BillResponse add(BillCreate create);

    BillResponse update(BillUpdate update);

    void delete(Long id);

    void changeStatus(Long id, Integer status, Staff staff);

    Bill findEntityById(Long id);

    BillResponse findResponseById(Long id);

    Page<BillProjection> getAllPage(int page, int size);

    List<BillProjection> getAllList();

    Page<BillProjection> findByStatusPage(int page, int size, Integer status);

    List<BillProjection> findByStatusList(Integer status);

    List<BillForCustomerProjection> listBillForCustomer(Long idCustomer);

    Integer countBillByStatus(Integer status);

    void changeStatus(Integer status, Long id);

    Bill changeAddress(Long billId, BillAddressChange change);

    Bill addEntity(Bill bill);

    boolean existByCode(String code);
}
