package com.genc.collection_service.client;

import com.genc.collection_service.dto.LoanApplicationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "loan-service")
public interface LoanClient {

    @GetMapping("/loan/applications/{id}")
    LoanApplicationDTO getApplicationById(@PathVariable("id") Long id);
}
