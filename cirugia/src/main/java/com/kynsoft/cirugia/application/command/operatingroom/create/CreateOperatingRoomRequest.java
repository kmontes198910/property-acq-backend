package com.kynsoft.cirugia.application.command.operatingroom.create;

import com.kynsoft.cirugia.domain.enums.OperatingRoomStatus;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CreateOperatingRoomRequest {
    private String roomNumber;
    private String name;
    private String location;
    private String floor;
    private String wing;
    private String type;
    private Integer size;
    private Integer capacity;
    private Boolean hasVentilationSystem;
    private Boolean hasAnesthesiaEquipment;
    private Boolean hasSurgicalLights;
    private Boolean hasMonitoringSystems;
    private Boolean hasOxygenSupply;
    private Boolean hasXRayCapability;
    private Boolean hasLaserEquipment;
    private Boolean hasRoboticsSystem;
    private String specialFeatures;
    private OperatingRoomStatus status;
    private LocalDateTime lastMaintenanceDate;
    private LocalDateTime nextMaintenanceDate;
    private UUID businessId;
}