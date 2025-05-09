package com.kynsoft.cirugia.application.query.recoverybed.getbyid;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.cirugia.domain.dto.RecoveryBed;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class RecoveryBedResponse implements IResponse, Serializable {
    private final UUID id;
    private final String bedNumber;
    private final String location;
    private final String type;
    private final String status;
    private final UUID businessId;
    private final String floor;
    private final UUID roomId;
    private final Boolean hasMonitor;
    private final Boolean hasOxygenSupply;
    private final LocalDateTime lastMaintenanceDate;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final UUID createdBy;
    private final UUID updatedBy;
    
    public RecoveryBedResponse(RecoveryBed recoveryBed) {
        this.id = recoveryBed.getId();
        this.bedNumber = recoveryBed.getBedNumber();
        this.location = recoveryBed.getLocation();
        this.type = recoveryBed.getType();
        this.status = recoveryBed.getStatus();
        this.businessId = recoveryBed.getBusinessId();
        this.floor = recoveryBed.getFloor();
        this.roomId = recoveryBed.getRoomId();
        this.hasMonitor = recoveryBed.getHasMonitor();
        this.hasOxygenSupply = recoveryBed.getHasOxygenSupply();
        this.lastMaintenanceDate = recoveryBed.getLastMaintenanceDate();
        this.createdAt = recoveryBed.getCreatedAt();
        this.updatedAt = recoveryBed.getUpdatedAt();
        this.createdBy = recoveryBed.getCreatedBy();
        this.updatedBy = recoveryBed.getUpdatedBy();
    }
}