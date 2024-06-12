package com.java5.assignment.repository;

import com.java5.assignment.entity.FavoriteList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteListRepository extends JpaRepository<FavoriteList, Long> {

    @Query("SELECT f FROM FavoriteList f WHERE f.customer.id = :customerId AND f.product.id = :productId")
    List<FavoriteList> existsByProductIdAndCustomerId(@Param("productId") Long productId,
                                                      @Param("customerId") Long customerId);

    @Query("SELECT f.id FROM FavoriteList f WHERE f.customer.id = :customerId AND f.product.id = :productId")
    Long findByProductIdAndCustomerId(@Param("productId") Long productId,
                                      @Param("customerId") Long customerId);
}
