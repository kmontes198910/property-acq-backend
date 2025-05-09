package com.kynsoft.cirugia.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecoveryBed implements Serializable {
    private UUID id;
    private String bedNumber;
    private String location;
    private String type;
    private String status;
    private String floor;
    private UUID recoveryRoomId;
    private Boolean hasMonitor;
    private Boolean hasOxygenSupply;
    private LocalDateTime lastMaintenanceDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private UUID createdBy;
    private UUID updatedBy;
    private UUID businessId;
}