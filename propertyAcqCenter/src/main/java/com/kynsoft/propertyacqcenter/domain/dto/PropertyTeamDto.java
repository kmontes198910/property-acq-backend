package com.kynsoft.propertyacqcenter.domain.dto;

import java.time.LocalDateTime;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyTeamDto {
    private UUID id;
    private PropertyDto property;
    private CompanyContactDto contact;
    private String profile;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
}
