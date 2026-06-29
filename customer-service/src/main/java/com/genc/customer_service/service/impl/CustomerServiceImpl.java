package com.genc.customer_service.service.impl;

import com.genc.customer_service.dto.CustomerRequestDTO;
import com.genc.customer_service.dto.CustomerResponseDTO;
import com.genc.customer_service.exception.ServiceException;
import com.genc.customer_service.mapper.CustomerMapper;
import com.genc.customer_service.model.Customer;
import com.genc.customer_service.repository.CustomerRepository;
import com.genc.customer_service.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponseDTO createCustomer(CustomerRequestDTO request) {
        customerRepository.findByEmail(request.getEmail()).ifPresent(c -> {
            throw new ServiceException("Email already exists");
        });

        Customer saved = customerRepository.save(CustomerMapper.toEntity(request));
        return CustomerMapper.toResponse(saved);
    }

    @Override
    public List<CustomerResponseDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(CustomerMapper::toResponse)
                .toList();
    }

    @Override
    public CustomerResponseDTO getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ServiceException("Customer not found: " + id));
        return CustomerMapper.toResponse(customer);
    }
}