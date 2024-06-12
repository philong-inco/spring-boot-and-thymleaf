package com.java5.assignment.repository;

import com.java5.assignment.entity.Color;
import com.java5.assignment.entity.Size;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {
    @Query("SELECT c FROM Size c")
    Page<Size> getAll(Pageable pageable);

    @Query("SELECT c FROM Size c WHERE c.status = :status")
    Page<Size> findByStatus(Pageable pageable, @Param("status") Integer status);

    @Query("SELECT c FROM Size c WHERE c.status = :status")
    List<Size> findByStatusList(@Param("status") Integer status);

    Optional<Size> findByName(String name);

    Optional<Size> findByCode(String code);

    boolean existsByName(String name);

    boolean existsByCode(String code);

    @Query("SELECT c FROM Size c WHERE c.name = :name AND c.id <> :id")
    List<Size> existsByNameAndDifferentId(@Param("name") String name, @Param("id") Long id);
}
