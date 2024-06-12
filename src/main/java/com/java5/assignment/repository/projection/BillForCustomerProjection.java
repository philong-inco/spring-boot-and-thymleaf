package com.java5.assignment.repository.projection;

import com.java5.assignment.entity.Bill;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Bill.class})
public interface BillForCustomerProjection {
    @Value("#{target.bill_code}")
    String getCode();

    @Value("#{target.bill_money_total}")
    String getMoneyTotal();

    @Value("#{target.bill_product_total}")
    String getProductTotal();

    @Value("#{target.bill_status}")
    Integer getStatus();

    @Value("#{target.bill_id}")
    Long getId();

}
