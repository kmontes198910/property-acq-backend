package com.kynsoft.cirugia.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * DTO que representa una sala de recuperación que contiene múltiples camas.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecoveryRoom implements Serializable {
    
    private UUID id;
    private String name;
    private String description;
    private String floor;
    private String wing;
    private Integer capacity;
    private String status;
    private UUID businessId;
    private String roomType;
    private Boolean isActive;
    private String additionalInfo;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    
    @Builder.Default
    private Set<UUID> bedIds = new HashSet<>();
}