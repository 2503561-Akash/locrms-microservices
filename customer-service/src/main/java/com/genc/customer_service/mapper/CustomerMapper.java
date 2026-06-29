package com.genc.customer_service.mapper;

import com.genc.customer_service.dto.CustomerRequestDTO;
import com.genc.customer_service.dto.CustomerResponseDTO;
import com.genc.customer_service.model.Customer;

public final class CustomerMapper {

    private CustomerMapper() {}

    public static Customer toEntity(CustomerRequestDTO dto) {
        Customer customer = new Customer();
        customer.setFullName(dto.getFullName());
        customer.setEmail(dto.getEmail());
        customer.setPhoneNumber(dto.getPhoneNumber());
        return customer;
    }

    public static CustomerResponseDTO toResponse(Customer customer) {
        return CustomerResponseDTO.builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .build();
    }
}