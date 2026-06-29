package com.genc.customer_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerRequestDTO {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @Email(message = "Email format is invalid")
    @NotBlank(message = "Email is required")
    private String email;

    private String phoneNumber;
}