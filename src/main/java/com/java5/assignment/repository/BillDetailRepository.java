package com.java5.assignment.repository;

import com.java5.assignment.dto.response.BillDetailResponse;
import com.java5.assignment.dto.response.BillResponse;
import com.java5.assignment.entity.BillDetail;
import com.java5.assignment.repository.projection.BillDetailProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetail, Long> {

    @Query(value = """
            SELECT
            p.name AS product_name,
            c.name AS color_name,
            s.name AS size_name,
            bd.amount AS bill_amount,
            bd.price AS bill_price,
            (bd.amount * bd.price) AS price_total
            FROM bill_detail bd LEFT JOIN product_detail pd ON bd.product_detail_id = pd.id
            LEFT JOIN color c ON pd.color_id = c.id
            LEFT JOIN size s ON pd.size_id = s.id
            LEFT JOIN product p ON pd.product_id = p.id
            WHERE bd.bill_id = :billId
            """, nativeQuery = true)
    List<BillDetailProjection> getBillDetailForClientByBillId(@Param("billId") Long billId);

    @Query("SELECT dt FROM BillDetail dt WHERE dt.bill.id = :id")
    List<BillDetail> getBillDetailByBillId(@Param("id") Long billId);
}
