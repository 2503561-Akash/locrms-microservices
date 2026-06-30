package com.genc.collection_service.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanApplicationDTO {
    private Long applicationId;
    private Long customerId;
    private Double requestedAmount;
}
