package com.java5.assignment.repository;

import com.java5.assignment.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Category findByName(String name);

    @Query("SELECT c FROM Category c")
    Page<Category> getAll(Pageable pageable);

    @Query("SELECT c FROM Category c")
    List<Category> getAll();

    @Query("SELECT c FROM Category c WHERE c.status = :status")
    Page<Category> findByStatus(Pageable pageable, @Param("status") Integer status);

    @Query("SELECT c FROM Category c WHERE c.status = :status")
    List<Category> findByStatus(@Param("status") Integer status);

    @Query("SELECT c FROM Category c WHERE c.id = :id")
    Optional<Category> findById(@Param("id") Long id);

    boolean existsByCode(String code);

    boolean existsByName(String name);

    @Query("SELECT c FROM Category c WHERE c.name = :name AND c.id <> :id")
    List<Category> existByNameAndDifferentId(@Param("name") String name, @Param("id") Long id);
}
