package com.java5.assignment.repository;

import com.java5.assignment.dto.response.StaffResponse;
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
public interface StaffRepository extends JpaRepository<Staff, Long> {
    @Query("SELECT s FROM Staff s")
    Page<Staff> getAll(Pageable pageable);

    @Query("SELECT s FROM Staff s WHERE s.status = :status")
    Page<Staff> findByStatus(Pageable pageable, @Param("status") Integer status);

    @Query("SELECT s FROM Staff s WHERE s.id = :id")
    Optional<Staff> findById(@Param("id") Long id);

    @Query("SELECT s FROM Staff  s WHERE s.username = :username")
    Optional<Staff> findByUsername(@Param("username") String username);

    @Query("SELECT s FROM Staff s WHERE s.name = :name")
    List<Staff> findByName(@Param("name") String name);

    @Query("SELECT s FROM Staff s WHERE s.role.id = :roleId")
    List<Staff> findByRoleId(@Param("roleId") Long roleId);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    @Query("SELECT s FROM Staff s WHERE s.email = :email AND s.id <> :id")
    List<Staff> existByEmailAndDifferentId(@Param("email") String email,@Param("id") Long id);
}
