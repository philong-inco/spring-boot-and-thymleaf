package com.java5.assignment.service;

import com.java5.assignment.dto.request.CustomerCreate;
import com.java5.assignment.dto.request.CustomerUpdate;
import com.java5.assignment.dto.request.StaffCreate;
import com.java5.assignment.dto.request.StaffUpdate;
import com.java5.assignment.dto.response.CustomerResponse;
import com.java5.assignment.dto.response.StaffResponse;
import com.java5.assignment.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CustomerService {
    Page<CustomerResponse> getAll(Pageable pageable);

    Page<CustomerResponse> findByStatus(Pageable pageable, Integer status);

    CustomerResponse findById(Long id);

    List<CustomerResponse> findByRoleId(Long roleId);

    boolean existByEmail(String email);

    boolean existByCode(String code);

    boolean existByPhone(String phone);

    boolean existByEmailAndDifferentId(String email, Long id);

    boolean existByPhoneAndDifferentId(String phone, Long id);

    CustomerResponse add(CustomerCreate create);

    CustomerResponse update(CustomerUpdate update);

    void delete(Long id);

    Customer createCustomerWithSessionId(String sessionId);

    Customer findBySessionId(String sessionId);

    Customer updateForCustomerGuest(CustomerUpdate update);

    Customer findByIdEntity(Long id);

    Page<CustomerResponse> findByNameOrPhoneOrEmailOrCode(String key, Pageable pageable);
}
