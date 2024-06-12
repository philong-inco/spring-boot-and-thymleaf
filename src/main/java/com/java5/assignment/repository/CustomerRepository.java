package com.java5.assignment.repository;

import com.java5.assignment.dto.request.CustomerUpdate;
import com.java5.assignment.dto.response.CustomerResponse;
import com.java5.assignment.entity.Customer;
import com.java5.assignment.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c WHERE c.email = :email")
    Optional<Customer> findByEmail(@Param("email") String email);

    @Query("SELECT s FROM Customer s")
    Page<Customer> getAll(Pageable pageable);

    @Query("SELECT s FROM Customer s WHERE s.status = :status")
    Page<Customer> findByStatus(Pageable pageable, @Param("status") Integer status);

    @Query("SELECT s FROM Customer s WHERE s.id = :id")
    Optional<Customer> findById(@Param("id") Long id);

    @Query("SELECT s FROM Customer s WHERE s.role.id = :roleId")
    List<Customer> findByRoleId(@Param("roleId") Long roleId);

    boolean existsByEmail(String email);

    boolean existsByCode(String code);

    boolean existsByPhone(String phone);

    @Query("SELECT s FROM Customer s WHERE s.email = :email AND s.id <> :id")
    List<Customer> existByEmailAndDifferentId(@Param("email") String email, @Param("id") Long id);

    @Query("SELECT s FROM Customer s WHERE s.phone = :phone AND s.id <> :id")
    List<Customer> existByPhoneAndDifferentId(@Param("phone") String phone, @Param("id") Long id);

    @Query("SELECT s FROM Customer s WHERE s.sessionId = :sessionId")
    Customer findBySessionId(@Param("sessionId") String sessionId);

    @Query("SELECT c FROM Customer c WHERE LOWER(c.name) LIKE %:key% OR LOWER(c.phone) LIKE %:key% OR LOWER(c.email) LIKE %:key% OR LOWER(c.code) LIKE %:key%")
    Page<Customer> findByNameOrPhoneOrEmailOrCode(@Param("key") String key, Pageable pageable);

}
