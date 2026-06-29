package com.genc.customer_service.service;

import com.genc.customer_service.dto.CustomerRequestDTO;
import com.genc.customer_service.dto.CustomerResponseDTO;

import java.util.List;

public interface CustomerService {
    CustomerResponseDTO createCustomer(CustomerRequestDTO request);
    List<CustomerResponseDTO> getAllCustomers();
    CustomerResponseDTO getCustomerById(Long id);
}