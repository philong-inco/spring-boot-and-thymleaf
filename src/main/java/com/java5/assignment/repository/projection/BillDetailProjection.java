package com.java5.assignment.repository.projection;

import com.java5.assignment.entity.Bill;
import com.java5.assignment.entity.BillDetail;
import com.java5.assignment.entity.Color;
import com.java5.assignment.entity.Product;
import com.java5.assignment.entity.ProductDetail;
import com.java5.assignment.entity.Size;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

@Projection(types = {Bill.class, BillDetail.class, Size.class, Color.class, ProductDetail.class, Product.class})
public interface BillDetailProjection {

    @Value("#{target.product_name}")
    String getProductName();

    @Value("#{target.color_name}")
    String getColorName();

    @Value("#{target.size_name}")
    String getSizeName();

    @Value("#{target.bill_amount}")
    Integer getBillAmount();

    @Value("#{target.bill_price}")
    Float getBillPrice();

    @Value("#{target.price_total}")
    Float getPriceTotal();

}
