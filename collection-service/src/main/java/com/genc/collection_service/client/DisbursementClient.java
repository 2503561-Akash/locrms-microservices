package com.genc.collection_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.Map;

@FeignClient(name = "disbursement-service")
public interface DisbursementClient {

    @GetMapping("/loan/repayment/{id}")
    List<Map<String, Object>> getRepaymentsForLoan(@PathVariable("id") Long id);
}
