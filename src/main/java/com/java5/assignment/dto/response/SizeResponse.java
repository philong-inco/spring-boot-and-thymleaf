package com.java5.assignment.dto.response;

import com.java5.assignment.common.Constants;
import com.java5.assignment.common.ConvertTime;
import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class SizeResponse {
    Long id;
    String code;
    String name;
    Integer status;
    String createAt;
    String modifyAt;
    String createBy;
    String modifyBy;

    public void convertTime(){
        this.createAt = ConvertTime.convert(this.createAt);
        this.modifyAt = ConvertTime.convert(this.modifyAt);
    }
}
