package com.genc.collection_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "collection_cases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollectionCase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "caseId")
    private Long caseId;

    @Column(name = "applicationId", nullable = false)
    private Long applicationId;

    @Column(name = "customerId", nullable = false)
    private Long customerId;

    @Column(name = "amountDue", nullable = false)
    private Double amountDue;

    @Column(name = "daysPastDue", nullable = false)
    private Integer daysPastDue;

    @Column(name = "assignedAgentId")
    private Long assignedAgentId;

    @Column(name = "recoveryStatus", nullable = false)
    private String recoveryStatus; // PENDING_ASSIGNMENT, ASSIGNED, RECOVERED, WRITTEN_OFF, ESCALATED

    @Column(name = "createdDate", nullable = false)
    private LocalDate createdDate;

    @Column(name = "updatedDate")
    private LocalDate updatedDate;
}
