package com.genc.collection_service.repository;

import com.genc.collection_service.model.RecoveryAction;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RecoveryActionRepository extends JpaRepository<RecoveryAction, Long> {
    List<RecoveryAction> findByCaseId(Long caseId);
}
