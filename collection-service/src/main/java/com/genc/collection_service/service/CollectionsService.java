package com.genc.collection_service.service;

import com.genc.collection_service.dto.CollectionCaseDTO;
import com.genc.collection_service.dto.RecoveryActionDTO;
import java.util.List;
import java.util.Map;

public interface CollectionsService {
    void flagDelinquentAccount(Long applicationId, Integer daysPastDue, Double amountDue);
    CollectionCaseDTO assignToAgent(Long caseId, Long agentId);
    RecoveryActionDTO logRecoveryAction(Long caseId, String actionType, String actionDetails, String recordedBy);
    CollectionCaseDTO updateStatus(Long caseId, String status);
    
    CollectionCaseDTO getCaseById(Long caseId);
    List<CollectionCaseDTO> getAllCases();
    List<CollectionCaseDTO> getCasesByAgent(Long agentId);
    List<RecoveryActionDTO> getRecoveryActionsByCase(Long caseId);
    Map<String, Object> getCollectionStats();

    // Legacy method signatures for compilation compatibility if needed
    void deleteCollection(Long logId);
}
