package com.java5.assignment.dto.request;

import com.java5.assignment.entity.Customer;
import com.java5.assignment.entity.Staff;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BillCreate {
    BigDecimal moneyTotal;
    Integer productTotal;

    Integer status;
    String note;
    Long customerId;

    Long staffId;


}
