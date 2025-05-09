package com.kynsoft.finamer.digitalsignature.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO para gestionar los certificados de firma digital en la API
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DigitalSignatureCertificateDto {
    
    private UUID id;
    
    private UUID userId;
    
    private String certificateName;
    
    private String certificateP12Base64;
    
    private String certificatePassword;
    
    private LocalDateTime expirationDate;
    
    private Boolean isActive;
    
    private UUID businessId;
    
    private String businessName;
    
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
}