package com.kynsoft.propertyacqcenter.domain.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OwnerDocumentDto {

    private UUID id;
    private String fileName;
    private String document;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private OwnerShipLegalEntityDto owner;
}
