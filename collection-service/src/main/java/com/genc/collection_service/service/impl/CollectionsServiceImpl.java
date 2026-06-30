package com.genc.collection_service.service.impl;

import com.genc.collection_service.client.LoanClient;
import com.genc.collection_service.dto.CollectionCaseDTO;
import com.genc.collection_service.dto.LoanApplicationDTO;
import com.genc.collection_service.dto.RecoveryActionDTO;
import com.genc.collection_service.exception.ServiceException;
import com.genc.collection_service.mapper.CollectionMapper;
import com.genc.collection_service.model.CollectionCase;
import com.genc.collection_service.model.RecoveryAction;
import com.genc.collection_service.repository.CollectionCaseRepository;
import com.genc.collection_service.repository.RecoveryActionRepository;
import com.genc.collection_service.service.CollectionsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CollectionsServiceImpl implements CollectionsService {

    private final CollectionCaseRepository collectionCaseRepository;
    private final RecoveryActionRepository recoveryActionRepository;
    private final LoanClient loanClient;
    private final CollectionMapper collectionMapper;

    public CollectionsServiceImpl(CollectionCaseRepository collectionCaseRepository,
                                  RecoveryActionRepository recoveryActionRepository,
                                  LoanClient loanClient,
                                  CollectionMapper collectionMapper) {
        this.collectionCaseRepository = collectionCaseRepository;
        this.recoveryActionRepository = recoveryActionRepository;
        this.loanClient = loanClient;
        this.collectionMapper = collectionMapper;
    }

    @Override
    @Transactional
    public void flagDelinquentAccount(Long applicationId, Integer daysPastDue, Double amountDue) {
        CollectionCase existing = collectionCaseRepository.findByApplicationId(applicationId).orElse(null);

        if (existing != null) {
            existing.setDaysPastDue(daysPastDue);
            existing.setAmountDue(existing.getAmountDue() + amountDue);
            existing.setUpdatedDate(LocalDate.now());
            collectionCaseRepository.save(existing);
        } else {
            Long customerId = 1L; // default fallback
            try {
                LoanApplicationDTO loanApp = loanClient.getApplicationById(applicationId);
                if (loanApp != null && loanApp.getCustomerId() != null) {
                    customerId = loanApp.getCustomerId();
                }
            } catch (Exception e) {
                // Keep default customerId if loan-service call fails
            }

            CollectionCase newCase = CollectionCase.builder()
                    .applicationId(applicationId)
                    .customerId(customerId)
                    .amountDue(amountDue)
                    .daysPastDue(daysPastDue)
                    .recoveryStatus("PENDING_ASSIGNMENT")
                    .createdDate(LocalDate.now())
                    .updatedDate(LocalDate.now())
                    .build();
            collectionCaseRepository.save(newCase);
        }
    }

    @Override
    @Transactional
    public CollectionCaseDTO assignToAgent(Long caseId, Long agentId) {
        CollectionCase c = collectionCaseRepository.findById(caseId)
                .orElseThrow(() -> new ServiceException("Collection case not found: " + caseId));
        c.setAssignedAgentId(agentId);
        c.setRecoveryStatus("ASSIGNED");
        c.setUpdatedDate(LocalDate.now());
        CollectionCase saved = collectionCaseRepository.save(c);
        return collectionMapper.toCollectionCaseDTO(saved);
    }

    @Override
    @Transactional
    public RecoveryActionDTO logRecoveryAction(Long caseId, String actionType, String actionDetails, String recordedBy) {
        CollectionCase c = collectionCaseRepository.findById(caseId)
                .orElseThrow(() -> new ServiceException("Collection case not found: " + caseId));

        RecoveryAction action = RecoveryAction.builder()
                .caseId(caseId)
                .actionType(actionType)
                .actionDetails(actionDetails)
                .actionDate(LocalDateTime.now())
                .recordedBy(recordedBy)
                .build();
        RecoveryAction savedAction = recoveryActionRepository.save(action);

        // Update case status if needed
        c.setUpdatedDate(LocalDate.now());
        collectionCaseRepository.save(c);

        return collectionMapper.toRecoveryActionDTO(savedAction);
    }

    @Override
    @Transactional
    public CollectionCaseDTO updateStatus(Long caseId, String status) {
        CollectionCase c = collectionCaseRepository.findById(caseId)
                .orElseThrow(() -> new ServiceException("Collection case not found: " + caseId));
        c.setRecoveryStatus(status);
        c.setUpdatedDate(LocalDate.now());
        CollectionCase saved = collectionCaseRepository.save(c);
        return collectionMapper.toCollectionCaseDTO(saved);
    }

    @Override
    public CollectionCaseDTO getCaseById(Long caseId) {
        CollectionCase c = collectionCaseRepository.findById(caseId)
                .orElseThrow(() -> new ServiceException("Collection case not found: " + caseId));
        return collectionMapper.toCollectionCaseDTO(c);
    }

    @Override
    public List<CollectionCaseDTO> getAllCases() {
        return collectionCaseRepository.findAll().stream()
                .map(collectionMapper::toCollectionCaseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<CollectionCaseDTO> getCasesByAgent(Long agentId) {
        return collectionCaseRepository.findByAssignedAgentId(agentId).stream()
                .map(collectionMapper::toCollectionCaseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RecoveryActionDTO> getRecoveryActionsByCase(Long caseId) {
        return recoveryActionRepository.findByCaseId(caseId).stream()
                .map(collectionMapper::toRecoveryActionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, Object> getCollectionStats() {
        List<CollectionCase> all = collectionCaseRepository.findAll();
        long total = all.size();
        long pending = all.stream().filter(c -> "PENDING_ASSIGNMENT".equalsIgnoreCase(c.getRecoveryStatus())).count();
        long assigned = all.stream().filter(c -> "ASSIGNED".equalsIgnoreCase(c.getRecoveryStatus())).count();
        long recovered = all.stream().filter(c -> "RECOVERED".equalsIgnoreCase(c.getRecoveryStatus())).count();
        long writtenOff = all.stream().filter(c -> "WRITTEN_OFF".equalsIgnoreCase(c.getRecoveryStatus())).count();
        double totalAmountDue = all.stream()
                .filter(c -> !"RECOVERED".equalsIgnoreCase(c.getRecoveryStatus()) && !"WRITTEN_OFF".equalsIgnoreCase(c.getRecoveryStatus()))
                .mapToDouble(CollectionCase::getAmountDue)
                .sum();

        return Map.of(
                "totalCases", total,
                "pendingCases", pending,
                "assignedCases", assigned,
                "recoveredCases", recovered,
                "writtenOffCases", writtenOff,
                "totalAmountDue", totalAmountDue
        );
    }

    @Override
    @Transactional
    public void deleteCollection(Long logId) {
        if (!collectionCaseRepository.existsById(logId)) {
            throw new ServiceException("Collection case not found: " + logId);
        }
        collectionCaseRepository.deleteById(logId);
    }

}
