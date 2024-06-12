package com.java5.assignment.mapper;

import com.java5.assignment.dto.request.CustomerCreate;
import com.java5.assignment.dto.request.CustomerUpdate;
import com.java5.assignment.dto.request.StaffCreate;
import com.java5.assignment.dto.request.StaffUpdate;
import com.java5.assignment.dto.response.CustomerResponse;
import com.java5.assignment.dto.response.StaffResponse;
import com.java5.assignment.entity.Customer;
import com.java5.assignment.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface CustomerMapper {
    Customer createToEntity(CustomerCreate create);

    Customer updateToEntity(CustomerUpdate update, Customer customer);

    CustomerResponse entityToResponse(Customer customer);

    Page<CustomerResponse> listEntityToListResponse(Page<Customer> customerPage);

    List<CustomerResponse> listEntityToListResponse(List<Customer> customerList);
}
