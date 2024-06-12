package com.java5.assignment.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerCreate {
    @NotEmpty(message = "Name cannot is blank")
    @Pattern(regexp = "^[a-zA-Z\\p{L}\\s]+$", message = "Invalid name")
    String name;
    @NotEmpty(message = "phone cannot is blank")
    String phone;
    @NotEmpty(message = "email cannot is blank")
    @Email(message = "Invalid email")
    String email;

    @NotEmpty(message = "password cannot is blank")
    String password;

    @NotEmpty(message = "password comfirm cannot is blank")
    String passwordComfirm;

    @NotEmpty(message = "address cannot is blank")
    String address;

    @NotNull(message = "Status cannot null")
    Integer status;

}
