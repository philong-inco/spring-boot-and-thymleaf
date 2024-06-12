package com.java5.assignment.repository;

import com.java5.assignment.entity.Color;
import com.java5.assignment.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {


    @Query("SELECT c FROM Role c")
    Page<Role> getAll(Pageable pageable);

    @Query("SELECT c FROM Role c WHERE c.status = :status")
    Page<Role> findByStatus(Pageable pageable, @Param("status") Integer status);

    @Query("SELECT c FROM Role c WHERE c.status = :status")
    List<Role> findByStatusList(@Param("status") Integer status);

    Optional<Role> findByName(String name);

    boolean existsByName(String name);


    @Query("SELECT c FROM Role c WHERE c.name = :name AND c.id <> :id")
    List<Role> existsByNameAndDifferentId(@Param("name") String name, @Param("id") Long id);
}
