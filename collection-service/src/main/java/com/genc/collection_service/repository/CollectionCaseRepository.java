package com.genc.collection_service.repository;

import com.genc.collection_service.model.CollectionCase;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CollectionCaseRepository extends JpaRepository<CollectionCase, Long> {
    Optional<CollectionCase> findByApplicationId(Long applicationId);
    List<CollectionCase> findByAssignedAgentId(Long agentId);
    List<CollectionCase> findByRecoveryStatus(String status);
}
