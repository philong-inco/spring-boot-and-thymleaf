package com.java5.assignment.dto.request;

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
public class SizeUpdate {

    Long id;
    @NotEmpty(message = "Khong de trong ten")
    String name;

    @Min(value = 0, message = "Trang thai khong hop le")
    @Max(value = 1, message = "Trang thai khong hop le")
    @NotNull(message = "Khong de trong trang thai")
    Integer status;
}
