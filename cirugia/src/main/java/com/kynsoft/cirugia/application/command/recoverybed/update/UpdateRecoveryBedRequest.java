package com.kynsoft.cirugia.application.command.recoverybed.update;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class UpdateRecoveryBedRequest {
    private String bedNumber;
    private String location;
    private String type;
    private String status;
    private UUID businessId;
    private String floor;
    private String room;
    private Boolean hasMonitor;
    private Boolean hasOxygenSupply;
    private LocalDateTime lastMaintenanceDate;
    private UUID updatedBy;
}