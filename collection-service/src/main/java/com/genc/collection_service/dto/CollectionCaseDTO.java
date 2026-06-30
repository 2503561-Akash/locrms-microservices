package com.genc.collection_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollectionCaseDTO {
    private Long caseId;

    @NotNull(message = "Application ID is required")
    private Long applicationId;

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @Positive(message = "Amount due must be positive")
    private Double amountDue;

    @PositiveOrZero(message = "Days past due cannot be negative")
    private Integer daysPastDue;

    private Long assignedAgentId;

    @NotBlank(message = "Recovery status is required")
    private String recoveryStatus; // PENDING_ASSIGNMENT, ASSIGNED, RECOVERED, WRITTEN_OFF, ESCALATED
    private LocalDate createdDate;
    private LocalDate updatedDate;
}
