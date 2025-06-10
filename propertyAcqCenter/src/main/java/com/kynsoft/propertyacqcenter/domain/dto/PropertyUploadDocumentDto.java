package com.kynsoft.propertyacqcenter.domain.dto;

import com.kynsoft.propertyacqcenter.domain.enums.PropertyDocumentType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyUploadDocumentDto {

    private UUID id;
    private String fileName;
    private String filePath;
    private String document;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private PropertyDto property;
    private PropertyDocumentType documentType;
}
