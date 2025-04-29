package com.kynsoft.cirugia.application.command.operatingroom.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class CreateOperatingRoomCommand implements ICommand {

    private UUID id;
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
    private String status;
    private LocalDateTime lastMaintenanceDate;
    private LocalDateTime nextMaintenanceDate;
    private UUID businessId;
    private UUID createdBy;

    public CreateOperatingRoomCommand(
            String roomNumber, String name, String location, String floor, String wing,
            String type, Integer size, Integer capacity, Boolean hasVentilationSystem,
            Boolean hasAnesthesiaEquipment, Boolean hasSurgicalLights, Boolean hasMonitoringSystems,
            Boolean hasOxygenSupply, Boolean hasXRayCapability, Boolean hasLaserEquipment,
            Boolean hasRoboticsSystem, String specialFeatures, String status,
            LocalDateTime lastMaintenanceDate, LocalDateTime nextMaintenanceDate,
            UUID businessId, UUID createdBy) {
        
        this.id = UUID.randomUUID();
        this.roomNumber = roomNumber;
        this.name = name;
        this.location = location;
        this.floor = floor;
        this.wing = wing;
        this.type = type;
        this.size = size;
        this.capacity = capacity;
        this.hasVentilationSystem = hasVentilationSystem;
        this.hasAnesthesiaEquipment = hasAnesthesiaEquipment;
        this.hasSurgicalLights = hasSurgicalLights;
        this.hasMonitoringSystems = hasMonitoringSystems;
        this.hasOxygenSupply = hasOxygenSupply;
        this.hasXRayCapability = hasXRayCapability;
        this.hasLaserEquipment = hasLaserEquipment;
        this.hasRoboticsSystem = hasRoboticsSystem;
        this.specialFeatures = specialFeatures;
        this.status = status;
        this.lastMaintenanceDate = lastMaintenanceDate;
        this.nextMaintenanceDate = nextMaintenanceDate;
        this.businessId = businessId;
        this.createdBy = createdBy;
    }

    public static CreateOperatingRoomCommand fromRequest(CreateOperatingRoomRequest request) {
        return new CreateOperatingRoomCommand(
                request.getRoomNumber(),
                request.getName(),
                request.getLocation(),
                request.getFloor(),
                request.getWing(),
                request.getType(),
                request.getSize(),
                request.getCapacity(),
                request.getHasVentilationSystem(),
                request.getHasAnesthesiaEquipment(),
                request.getHasSurgicalLights(),
                request.getHasMonitoringSystems(),
                request.getHasOxygenSupply(),
                request.getHasXRayCapability(),
                request.getHasLaserEquipment(),
                request.getHasRoboticsSystem(),
                request.getSpecialFeatures(),
                request.getStatus(),
                request.getLastMaintenanceDate(),
                request.getNextMaintenanceDate(),
                request.getBusinessId(),
                request.getCreatedBy()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateOperatingRoomMessage(id);
    }
}