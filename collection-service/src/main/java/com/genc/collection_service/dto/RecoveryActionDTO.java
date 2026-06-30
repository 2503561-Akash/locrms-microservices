package com.genc.collection_service.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecoveryActionDTO {
    private Long actionId;

    @NotNull(message = "Case ID is required")
    private Long caseId;

    @NotBlank(message = "Action type is required")
    private String actionType; // CALL, VISIT, EMAIL, LEGAL_NOTICE

    @NotBlank(message = "Action details are required")
    private String actionDetails;
    private LocalDateTime actionDate;
    private String recordedBy;
}
