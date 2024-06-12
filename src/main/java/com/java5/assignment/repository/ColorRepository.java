package com.java5.assignment.repository;

import com.java5.assignment.entity.Color;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
    @Query("SELECT c FROM Color c")
    Page<Color> getAll(Pageable pageable);

    @Query("SELECT c FROM Color c WHERE c.status = :status")
    Page<Color> findByStatus(Pageable pageable, @Param("status") Integer status);

    @Query("SELECT c FROM Color c WHERE c.status = :status")
    List<Color> findByStatusList(@Param("status") Integer status);

    Optional<Color> findByName(String name);

    Optional<Color> findByCode(String code);

    boolean existsByName(String name);

    boolean existsByCode(String code);

    @Query("SELECT c FROM Color c WHERE c.name = :name AND c.id <> :id")
    List<Color> existsByNameAndDifferentId(@Param("name") String name, @Param("id") Long id);
}