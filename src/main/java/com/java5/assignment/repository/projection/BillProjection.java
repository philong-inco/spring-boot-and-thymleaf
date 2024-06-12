package com.java5.assignment.repository.projection;

import com.java5.assignment.entity.Bill;
import com.java5.assignment.entity.Customer;
import com.java5.assignment.entity.Staff;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

import java.math.BigDecimal;

@Projection(types = {Bill.class, Staff.class, Customer.class})
public interface BillProjection {
    @Value("#{target.bill_id}")
    Long getBillId();
    @Value("#{target.bill_code}")
    String getBillCode();

    @Value("#{target.money_total}")
    BigDecimal getMoneyTotal();

    @Value("#{target.product_total}")
    Integer getProductTotal();

    @Value("#{target.bill_status}")
    Integer getBillStatus();

    @Value("#{target.customer_name}")
    String getCustomerName();

    @Value("#{target.customer_phone}")
    String getCustomerPhone();

    @Value("#{target.staff_name}")
    String getStaffName();

}
