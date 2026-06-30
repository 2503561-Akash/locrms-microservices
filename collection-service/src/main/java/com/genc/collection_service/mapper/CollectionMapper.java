package com.genc.collection_service.mapper;

import com.genc.collection_service.dto.CollectionCaseDTO;
import com.genc.collection_service.dto.RecoveryActionDTO;
import com.genc.collection_service.model.CollectionCase;
import com.genc.collection_service.model.RecoveryAction;
import org.springframework.stereotype.Component;

@Component
public class CollectionMapper {

    public CollectionCaseDTO toCollectionCaseDTO(CollectionCase c) {
        if (c == null) {
            return null;
        }
        return CollectionCaseDTO.builder()
                .caseId(c.getCaseId())
                .applicationId(c.getApplicationId())
                .customerId(c.getCustomerId())
                .amountDue(c.getAmountDue())
                .daysPastDue(c.getDaysPastDue())
                .assignedAgentId(c.getAssignedAgentId())
                .recoveryStatus(c.getRecoveryStatus())
                .createdDate(c.getCreatedDate())
                .updatedDate(c.getUpdatedDate())
                .build();
    }

    public RecoveryActionDTO toRecoveryActionDTO(RecoveryAction r) {
        if (r == null) {
            return null;
        }
        return RecoveryActionDTO.builder()
                .actionId(r.getActionId())
                .caseId(r.getCaseId())
                .actionType(r.getActionType())
                .actionDetails(r.getActionDetails())
                .actionDate(r.getActionDate())
                .recordedBy(r.getRecordedBy())
                .build();
    }
}

