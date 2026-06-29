package com.genc.customer_service.controller;

import com.genc.customer_service.dto.CustomerRequestDTO;
import com.genc.customer_service.dto.CustomerResponseDTO;
import com.genc.customer_service.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponseDTO create(@Valid @RequestBody CustomerRequestDTO request) {
        return customerService.createCustomer(request);
    }

    @GetMapping
    public List<CustomerResponseDTO> getAll() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public CustomerResponseDTO getById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }
}