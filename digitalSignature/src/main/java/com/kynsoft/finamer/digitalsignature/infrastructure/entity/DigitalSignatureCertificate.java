package com.kynsoft.finamer.digitalsignature.infrastructure.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidad que almacena los certificados digitales (P12) de las personas.
 * Cada certificado está asociado a un Business y a un usuario específico.
 */
@Entity
@Table(name = "digital_signature_certificates")
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class DigitalSignatureCertificate {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;
    
    @Column(name = "certificate_name", nullable = false)
    private String certificateName;

    @Column(name = "certificate_p12", nullable = false, columnDefinition = "bytea")
    private byte[] certificateP12;
    
    @Column(name = "certificate_password", nullable = false)
    private String certificatePassword;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "business_id", nullable = false)
    private UUID businessId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Business business;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
