package com.java5.assignment.dto.response;

import com.java5.assignment.common.ConvertTime;
import com.java5.assignment.entity.Bill;
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

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BillHistoryResponse {

    Long id;
    String note;

    String billCode;

    String staffName;

    String createAt;

    String modifyAt;

    String createBy;

    String modifyBy;

    public void convertTime() {
        this.createAt = ConvertTime.convert(this.createAt);
        this.modifyAt = ConvertTime.convert(this.modifyAt);
    }
}
