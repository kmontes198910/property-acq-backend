package com.kynsoft.propertyacqcenter.domain.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * DTO para representar tipos de empresas inmobiliarias
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyTypeDto {

    private UUID id;
    private String name;
    private String code;
    private String description;
    private String examples;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}