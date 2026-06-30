package com.genc.collection_service.controller;

import com.genc.collection_service.dto.CollectionCaseDTO;
import com.genc.collection_service.dto.RecoveryActionDTO;
import com.genc.collection_service.exception.ServiceException;
import com.genc.collection_service.service.CollectionsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/collections")
public class CollectionsController {

    private final CollectionsService collectionsService;

    public CollectionsController(CollectionsService collectionsService) {
        this.collectionsService = collectionsService;
    }

    @PostMapping("/flag")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> flagDelinquentAccount(
            @RequestParam("applicationId") Long applicationId,
            @RequestParam("daysPastDue") Integer daysPastDue,
            @RequestParam("amountDue") Double amountDue) {
        collectionsService.flagDelinquentAccount(applicationId, daysPastDue, amountDue);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/cases")
    @PreAuthorize("hasAnyRole('ADMIN', 'COLLECTION_AGENT')")
    public ResponseEntity<List<CollectionCaseDTO>> getAllCases() {
        return ResponseEntity.ok(collectionsService.getAllCases());
    }

    @GetMapping("/cases/{caseId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COLLECTION_AGENT')")
    public ResponseEntity<CollectionCaseDTO> getCaseById(@PathVariable("caseId") Long caseId) {
        return ResponseEntity.ok(collectionsService.getCaseById(caseId));
    }

    @GetMapping("/cases/agent/{agentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'COLLECTION_AGENT')")
    public ResponseEntity<List<CollectionCaseDTO>> getCasesByAgent(@PathVariable("agentId") Long agentId) {
        return ResponseEntity.ok(collectionsService.getCasesByAgent(agentId));
    }

    @PatchMapping("/cases/{caseId}/assign")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CollectionCaseDTO> assignToAgent(
            @PathVariable("caseId") Long caseId,
            @RequestBody Map<String, Long> requestBody) {
        Long agentId = requestBody.get("agentId");
        if (agentId == null) {
            throw new ServiceException("agentId is required");
        }
        return ResponseEntity.ok(collectionsService.assignToAgent(caseId, agentId));
    }

    @PostMapping("/cases/{caseId}/actions")
    @PreAuthorize("hasAnyRole('ADMIN', 'COLLECTION_AGENT')")
    public ResponseEntity<RecoveryActionDTO> logRecoveryAction(
            @RequestHeader("X-User-Username") String username,
            @PathVariable("caseId") Long caseId,
            @RequestBody Map<String, String> requestBody) {
        String actionType = requestBody.get("actionType");
        String actionDetails = requestBody.get("actionDetails");
        return ResponseEntity.ok(collectionsService.logRecoveryAction(caseId, actionType, actionDetails, username));
    }

    @GetMapping("/cases/{caseId}/actions")
    @PreAuthorize("hasAnyRole('ADMIN', 'COLLECTION_AGENT')")
    public ResponseEntity<List<RecoveryActionDTO>> getRecoveryActionsByCase(@PathVariable("caseId") Long caseId) {
        return ResponseEntity.ok(collectionsService.getRecoveryActionsByCase(caseId));
    }

    @PatchMapping("/cases/{caseId}/status")
    @PreAuthorize("hasAnyRole('ADMIN', 'COLLECTION_AGENT')")
    public ResponseEntity<CollectionCaseDTO> updateStatus(
            @PathVariable("caseId") Long caseId,
            @RequestBody Map<String, String> requestBody) {
        String status = requestBody.get("status");
        if (status == null || status.trim().isEmpty()) {
            throw new ServiceException("Status is required");
        }
        return ResponseEntity.ok(collectionsService.updateStatus(caseId, status));
    }

    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getCollectionStats() {
        return ResponseEntity.ok(collectionsService.getCollectionStats());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteCollection(@PathVariable("id") Long id) {
        collectionsService.deleteCollection(id);
        return ResponseEntity.noContent().build();
    }
}
