package com.java5.assignment.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
public class BillAddressChange {
    @NotBlank(message = "Vui lòng điền tên")
    String name;
    @NotBlank(message = "Vui lòng điền SĐT")
    @Size(min = 9, max = 12, message = "SDT từ 9-12 ký tự")
    String phone;

    @NotBlank(message = "Vui lòng điền địa chỉ")
    String address;
}
