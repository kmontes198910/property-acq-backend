package com.kynsoft.finamer.digitalsignature.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Respuesta para certificados de firma digital.
 * Usado para transformar los DTOs en respuestas más específicas.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DigitalSignatureCertificateResponse implements IResponse {
    private UUID id;
    private UUID userId;
    private String certificateName;
    private String certificateP12Base64;
    private Boolean isActive;
    private Boolean isPrimaryKey;
    private LocalDateTime expirationDate;
    private UUID businessId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Campos adicionales de metadatos
    private boolean hasExpired;
    private String certificateStatus;
    private long daysUntilExpiration;
}