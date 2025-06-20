package com.kynsoft.propertyacqcenter.domain.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeneralDocumentDto {

    private UUID id;
    private DocumentTypeDto documentType;
    private AdquisitionPropertyDto adquisitionProperty;
    private String fileName;
    private String filePath;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
}