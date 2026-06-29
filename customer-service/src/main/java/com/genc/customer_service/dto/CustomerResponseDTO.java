package com.genc.customer_service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerResponseDTO {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
}