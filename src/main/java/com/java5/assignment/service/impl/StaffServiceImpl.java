package com.java5.assignment.service.impl;

import com.java5.assignment.dto.request.StaffCreate;
import com.java5.assignment.dto.request.StaffUpdate;
import com.java5.assignment.dto.response.StaffResponse;
import com.java5.assignment.entity.Role;
import com.java5.assignment.entity.Staff;
import com.java5.assignment.mapper.StaffMapper;
import com.java5.assignment.repository.RoleRepository;
import com.java5.assignment.repository.StaffRepository;
import com.java5.assignment.service.StaffService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Primary
public class StaffServiceImpl implements StaffService {
    private StaffRepository repository;
    private RoleRepository roleRepository;
    private StaffMapper mapper;

    private PasswordEncoder passwordEncoder;

    public StaffServiceImpl(StaffRepository repository, @Qualifier("staffMapperImpl") StaffMapper mapper,
                            PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.repository = repository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public StaffResponse add(StaffCreate staffCreate) {
        Optional<Role> role = roleRepository.findById(staffCreate.getIdRole());
        if (role.isPresent()) {
            Staff staff = mapper.createToEntity(staffCreate);
            staff.setPassword(passwordEncoder.encode(staff.getPassword()));
            staff.setRole(role.get());
            return mapper.entityToResponse(repository.save(staff));
        }
        return null;
    }

    @Override
    public StaffResponse update(StaffUpdate staffUpdate) {
        Optional<Role> role = roleRepository.findById(staffUpdate.getIdRole());
        Optional<Staff> staff = repository.findById(staffUpdate.getId());
        if (role.isPresent() && staff.isPresent()) {
            Staff toEntity = mapper.updateToEntity(staffUpdate, staff.get());
            return mapper.entityToResponse(repository.save(toEntity));
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        Optional<Staff> staff = repository.findById(id);
        if (staff.isPresent())
            repository.delete(staff.get());
    }

    @Override
    public Page<StaffResponse> getAll(Pageable pageable) {
        return mapper.listEntityToListResponse(repository.getAll(pageable));
    }

    @Override
    public Page<StaffResponse> findByStatus(Pageable pageable, Integer status) {
        return mapper.listEntityToListResponse(repository.findByStatus(pageable, status));
    }

    @Override
    public boolean isExistByUsername(String username) {
        return repository.existsByUsername(username.trim());
    }

    @Override
    public StaffResponse findById(Long id) {
        return mapper.entityToResponse(repository.findById(id).orElse(null));
    }

    @Override
    public boolean existByEmail(String email) {
        return repository.existsByEmail(email.trim());
    }

    @Override
    public boolean existByEmailAndDifferentId(String email, Long id) {
        if (repository.existByEmailAndDifferentId(email.trim(), id).size() > 0)
            return true;
        return false;
    }
}
