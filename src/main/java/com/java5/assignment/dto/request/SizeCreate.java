package com.java5.assignment.dto.request;

import com.java5.assignment.common.Constants;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SizeCreate {
    @NotEmpty(message = "Khong de trong ten")
    String name;
    @NotNull(message = "Khong de trong trang thai")
    @Min(value = 0, message = "Trang thai khong hop le")
    @Min(value = 1, message = "Trang thai khong hop le")
    Integer status;
}
