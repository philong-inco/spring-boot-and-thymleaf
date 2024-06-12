package com.java5.assignment.service.impl;


import com.java5.assignment.common.GenerateCode;
import com.java5.assignment.dto.request.CustomerCreate;
import com.java5.assignment.dto.request.CustomerUpdate;
import com.java5.assignment.dto.response.CustomerResponse;
import com.java5.assignment.entity.Customer;
import com.java5.assignment.entity.Role;
import com.java5.assignment.mapper.CustomerMapper;
import com.java5.assignment.repository.CustomerRepository;
import com.java5.assignment.repository.RoleRepository;
import com.java5.assignment.service.CustomerService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.PropertyPermission;

@Component
@Primary
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository repository;

    private RoleRepository roleRepository;
    private GenerateCode generateCode;

    private CustomerMapper mapper;

    private PasswordEncoder passwordEncoder;

    public CustomerServiceImpl(CustomerRepository repository, GenerateCode generateCode, @Qualifier("customerMapperImpl") CustomerMapper mapper,
                               PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.repository = repository;
        this.generateCode = generateCode;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public Page<CustomerResponse> getAll(Pageable pageable) {
        return mapper.listEntityToListResponse(repository.getAll(pageable));
    }

    @Override
    public Page<CustomerResponse> findByStatus(Pageable pageable, Integer status) {
        return mapper.listEntityToListResponse(repository.findByStatus(pageable, status));
    }

    @Override
    public CustomerResponse findById(Long id) {
        return mapper.entityToResponse(repository.findById(id).orElse(null));
    }

    @Override
    public List<CustomerResponse> findByRoleId(Long roleId) {
        return mapper.listEntityToListResponse(repository.findByRoleId(roleId));
    }

    @Override
    public boolean existByEmail(String email) {
        return repository.existsByEmail(email.trim());
    }

    @Override
    public boolean existByCode(String code) {
        return repository.existsByCode(code.trim());
    }

    @Override
    public boolean existByPhone(String phone) {
        return repository.existsByPhone(phone.trim());
    }

    @Override
    public boolean existByEmailAndDifferentId(String email, Long id) {
        if (repository.existByEmailAndDifferentId(email.trim(), id).size() > 0)
            return true;
        return false;
    }

    @Override
    public boolean existByPhoneAndDifferentId(String phone, Long id) {
        if (repository.existByPhoneAndDifferentId(phone.trim(), id).size() > 0)
            return true;
        return false;
    }

    @Override
    public CustomerResponse add(CustomerCreate create) {
        Customer customer = mapper.createToEntity(create);
        do {
            customer.setCode(generateCode.generateCode(GenerateCode.CUSTOMER_PREFIX));
        } while (repository.existsByCode(customer.getCode()));
        customer.setPassword(passwordEncoder.encode(create.getPassword()));
        customer.setRole(roleRepository.findByName("CUSTOMER").orElse(null));
        return mapper.entityToResponse(repository.save(customer));
    }

    @Override
    public CustomerResponse update(CustomerUpdate update) {
        Optional<Customer> customer = repository.findById(update.getId());
        if (customer.isPresent()) {
            Customer toEntity = mapper.updateToEntity(update, customer.get());
            return mapper.entityToResponse(repository.save(toEntity));
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<Customer> customer = repository.findById(id);
        if (customer.isPresent()) {
            repository.delete(customer.get());
        }
    }

    @Override
    public Customer createCustomerWithSessionId(String sessionId) {
        Role roleCustomer = roleRepository.findByName("CUSTOMER").orElse(null);
        Customer customer = Customer.builder()
                .status(1)
                .role(roleCustomer)
                .name("GUEST")
                .sessionId(sessionId.trim())
                .build();
        return repository.save(customer);
    }

    @Override
    public Customer findBySessionId(String sessionId) {
        return repository.findBySessionId(sessionId.trim());
    }

    @Override
    public Customer updateForCustomerGuest(CustomerUpdate update) {
        Customer customer = repository.findById(update.getId()).orElse(null);
        if (customer != null) {
            customer.setName(update.getName().trim());
            customer.setPhone(update.getPhone().trim());
            customer.setAddress(update.getAddress().trim());
            return repository.save(customer);
        }
        return null;
    }

    @Override
    public Customer findByIdEntity(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Page<CustomerResponse> findByNameOrPhoneOrEmailOrCode(String key, Pageable pageable) {
        return mapper.listEntityToListResponse(repository.findByNameOrPhoneOrEmailOrCode(key.trim().toLowerCase(), pageable));
    }
}
