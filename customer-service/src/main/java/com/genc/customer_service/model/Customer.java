package com.genc.customer_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "Customer")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customerId")
    private Long customerId;

    @Column(name = "user_id", unique = true, nullable = false)
    @NotNull(message = "User ID is required")
    private Long userId;

    @Column(nullable = false)
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @Column(name = "fullName", nullable = false)
    @NotBlank(message = "Full name is required")
    @Size(max = 100, message = "Full name cannot exceed 100 characters")
    private String fullName;

    @Column(name = "phone_number")
    @Pattern(regexp = "^$|^\\d{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @Column(name = "panNumber")
    @Pattern(regexp = "^$|^[A-Z]{5}\\d{4}[A-Z]$", message = "PAN number must be valid (e.g. ABCDE1234F)")
    private String panNumber;

    @Column(name = "aadhaarNumber")
    @Pattern(regexp = "^$|^\\d{12}$", message = "Aadhaar number must be 12 digits")
    private String aadhaarNumber;

    @Column(name = "employment_status")
    private String employmentStatus;

    @Column(name = "monthly_income")
    @Positive(message = "Monthly income must be positive")
    private Double monthlyIncome;

    @Column(name = "monthly_liabilities")
    @PositiveOrZero(message = "Monthly liabilities cannot be negative")
    private Double monthlyLiabilities;

    @Column(name = "kycStatus", nullable = false)
    private String kycStatus; // PENDING, VERIFIED, REJECTED

    @Column(name = "kyc_rejection_reason")
    @Size(max = 255, message = "KYC rejection reason cannot exceed 255 characters")
    private String kycRejectionReason;

    @Column(name = "registrationDate")
    private LocalDate registrationDate;

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private KycDocument kycDocument;
}

