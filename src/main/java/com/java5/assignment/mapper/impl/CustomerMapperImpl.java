package com.java5.assignment.mapper.impl;

import com.java5.assignment.dto.request.CustomerCreate;
import com.java5.assignment.dto.request.CustomerUpdate;
import com.java5.assignment.dto.response.CustomerResponse;
import com.java5.assignment.entity.Customer;
import com.java5.assignment.mapper.CustomerMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerMapperImpl implements CustomerMapper {
    @Override
    public Customer createToEntity(CustomerCreate create) {
        Customer customer = Customer.builder()
                .email(create.getEmail().trim())
                .phone(create.getPhone().trim())
                .name(create.getName().trim())
                .address(create.getAddress().trim())
                .password(create.getPassword().trim())
                .status(create.getStatus())
                .build();
        return customer;
    }

    @Override
    public Customer updateToEntity(CustomerUpdate update, Customer customer) {
        customer.setName(update.getName());
        customer.setStatus(update.getStatus());
        customer.setAddress(update.getAddress());
        customer.setEmail(update.getEmail());
        customer.setPhone(update.getPhone());
        return customer;
    }

    @Override
    public CustomerResponse entityToResponse(Customer customer) {
       CustomerResponse response = CustomerResponse.builder()
               .id(customer.getId())
               .code(customer.getCode())
               .name(customer.getName())
               .phone(customer.getPhone())
               .email(customer.getEmail())
               .address(customer.getAddress())
               .status(customer.getStatus())
               .role(customer.getRole().getName())
               .createAt(String.valueOf(customer.getCreateAt()))
               .modifyAt(String.valueOf(customer.getModifyAt()))
               .createBy(customer.getCreateBy())
               .modifyBy(customer.getModifyBy())
               .build();
       response.convertTime();
       return response;
    }

    @Override
    public Page<CustomerResponse> listEntityToListResponse(Page<Customer> customerPage) {
       List<CustomerResponse> response = customerPage.getContent().stream()
               .map(this::entityToResponse).collect(Collectors.toList());
       return new PageImpl<>(response, customerPage.getPageable(), customerPage.getTotalElements());
    }

    @Override
    public List<CustomerResponse> listEntityToListResponse(List<Customer> customerList) {
        return customerList.stream().map(this::entityToResponse).collect(Collectors.toList());
    }


}
