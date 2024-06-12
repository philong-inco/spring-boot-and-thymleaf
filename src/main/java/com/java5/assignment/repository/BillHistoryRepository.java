package com.java5.assignment.repository;

import com.java5.assignment.entity.BillHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BillHistoryRepository extends JpaRepository<BillHistory, Long> {

    @Query("SELECT bh FROM BillHistory bh WHERE bh.bill.id = :billId ORDER BY bh.createAt DESC")
    List<BillHistory> getAllByBillId(@Param("billId") Long billId);
}
