package com.kynsoft.propertyacqcenter.domain.dto;

import com.kynsoft.propertyacqcenter.infrastructure.entity.enums.DocumentStatus;
import com.kynsoft.propertyacqcenter.infrastructure.entity.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentDto {
    private UUID id;
    private UUID legalEntityId;
    private String fileName;
    private String filePath;
    private DocumentType documentType;
    private String description;
    private Long fileSize;
    private String contentType;
    private LocalDateTime expirationDate;
    private String issuedBy;
    private LocalDateTime issuingDate;
    private DocumentStatus documentStatus;
    private LocalDateTime verificationDate;
    private UUID verifiedBy;
    private String documentNumber;
    private Boolean isOriginal;
    private String version;
    private Boolean renewalRequired;
    private LocalDateTime renewalDate;
    private String tags;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
}
