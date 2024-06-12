package com.java5.assignment.repository;

import com.java5.assignment.entity.Bill;
import com.java5.assignment.repository.projection.BillForCustomerProjection;
import com.java5.assignment.repository.projection.BillProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    @Query(value = """
            SELECT 
            b.id AS bill_id,
            b.code AS bill_code,
            b.money_total AS money_total,
            b.product_total AS product_total,
            b.status AS bill_status,
            c.name AS customer_name,
            c.phone AS customer_phone,
            s.name AS staff_name
            FROM bill b LEFT JOIN customer c ON b.customer_id = c.id
            LEFT JOIN staff s ON b.staff_id = s.id
            ORDER BY b.create_at
            """, nativeQuery = true)
    Page<BillProjection> getAllPage(Pageable pageable);

    @Query(value = """
            SELECT 
            b.id AS bill_id,
            b.code AS bill_code,
            b.money_total AS money_total,
            b.product_total AS product_total,
            b.status AS bill_status,
            c.name AS customer_name,
            c.phone AS customer_phone,
            s.name AS staff_name
            FROM bill b LEFT JOIN customer c ON b.customer_id = c.id
            LEFT JOIN staff s ON b.staff_id = s.id
            WHERE b.status = :status
            ORDER BY b.create_at
            """, nativeQuery = true)
    Page<BillProjection> findByStatusPage(Pageable pageable, @Param("status") Integer status);

    @Query(value = """
            SELECT 
            b.id AS bill_id,
            b.code AS bill_code,
            b.money_total AS money_total,
            b.product_total AS product_total,
            b.status AS bill_status,
            c.name AS customer_name,
            c.phone AS customer_phone,
            s.name AS staff_name
            FROM bill b LEFT JOIN customer c ON b.customer_id = c.id
            LEFT JOIN staff s ON b.staff_id = s.id
            ORDER BY b.create_at
            """, nativeQuery = true)
    List<BillProjection> getAllList();

    @Query(value = """
            SELECT 
            b.id AS bill_id,
            b.code AS bill_code,
            b.money_total AS money_total,
            b.product_total AS product_total,
            b.status AS bill_status,
            c.name AS customer_name,
            c.phone AS customer_phone,
            s.name AS staff_name
            FROM bill b LEFT JOIN customer c ON b.customer_id = c.id
            LEFT JOIN staff s ON b.staff_id = s.id
            WHERE b.status = :status
            ORDER BY b.create_at
            """, nativeQuery = true)
    List<BillProjection> findByStatusList(@Param("status") Integer status);

    boolean existsByCode(String code);

    @Query(value = """
            SELECT 
            b.code AS bill_code,
            b.money_total AS bill_money_total,
            b.product_total AS bill_product_total,
            b.status AS bill_status,
            b.id AS bill_id
            FROM bill b
            WHERE b.customer_id = :idCustomer
            ORDER BY b.create_at DESC
            """, nativeQuery = true)
    List<BillForCustomerProjection> listBillForCustomer(@Param(("idCustomer")) Long idCustomer);

    Integer countBillByStatus(Integer status);

    //    @Query("UPDATE Bill b SET b.status=:status WHERE b.id=:id")
//    void changeStatus(@Param("status") Integer status, @Param("id") Long id);

}
