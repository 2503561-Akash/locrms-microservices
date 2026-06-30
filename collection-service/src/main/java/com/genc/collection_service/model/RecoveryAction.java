package com.genc.collection_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "recovery_actions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecoveryAction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actionId")
    private Long actionId;

    @Column(name = "caseId", nullable = false)
    private Long caseId;

    @Column(name = "actionType", nullable = false)
    private String actionType; // CALL, VISIT, EMAIL, LEGAL_NOTICE

    @Column(name = "actionDetails")
    private String actionDetails;

    @Column(name = "actionDate", nullable = false)
    private LocalDateTime actionDate;

    @Column(name = "recordedBy")
    private String recordedBy; // agent username or ID
}
