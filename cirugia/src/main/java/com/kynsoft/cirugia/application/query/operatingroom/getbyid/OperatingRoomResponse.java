package com.kynsoft.cirugia.application.query.operatingroom.getbyid;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.cirugia.domain.dto.OperatingRoom;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class OperatingRoomResponse implements IResponse {
    private final UUID id;
    private final String roomNumber;           // Número de identificación del salón quirúrgico
    private final String name;                 // Nombre del salón quirúrgico (opcional)
    private final String location;             // Ubicación dentro del hospital/clínica
    private final String floor;                // Piso donde se encuentra
    private final String wing;                 // Ala o sección del edificio
    private final String type;                 // Tipo de salón (general, especializado, etc.)
    private final Integer size;                // Tamaño en metros cuadrados
    private final Integer capacity;            // Capacidad de personas
    private final Boolean hasVentilationSystem;// Si tiene sistema de ventilación especializado
    private final Boolean hasAnesthesiaEquipment;  // Si tiene equipo de anestesia
    private final Boolean hasSurgicalLights;   // Si tiene luces quirúrgicas
    private final Boolean hasMonitoringSystems;// Si tiene sistemas de monitoreo
    private final Boolean hasOxygenSupply;     // Si tiene suministro de oxígeno
    private final Boolean hasXRayCapability;   // Si tiene capacidad para rayos X
    private final Boolean hasLaserEquipment;   // Si tiene equipo láser
    private final Boolean hasRoboticsSystem;   // Si tiene sistema robótico para cirugías
    private final String specialFeatures;      // Características especiales (texto libre)
    private final String status;               // Estado (AVAILABLE, IN_USE, MAINTENANCE, RESERVED)
    private final LocalDateTime lastMaintenanceDate; // Última fecha de mantenimiento
    private final LocalDateTime nextMaintenanceDate; // Próxima fecha programada para mantenimiento
    private final UUID businessId;             // ID del negocio/hospital/clínica
    private final LocalDateTime createdAt;     // Fecha de creación del registro
    private final LocalDateTime updatedAt;     // Fecha de actualización del registro
    private final UUID createdBy;              // Usuario que creó el registro
    private final UUID updatedBy;              // Usuario que actualizó el registro
    
    public OperatingRoomResponse(OperatingRoom operatingRoom) {
        this.id = operatingRoom.getId();
        this.roomNumber = operatingRoom.getRoomNumber();
        this.name = operatingRoom.getName();
        this.location = operatingRoom.getLocation();
        this.floor = operatingRoom.getFloor();
        this.wing = operatingRoom.getWing();
        this.type = operatingRoom.getType();
        this.size = operatingRoom.getSize();
        this.capacity = operatingRoom.getCapacity();
        this.hasVentilationSystem = operatingRoom.getHasVentilationSystem();
        this.hasAnesthesiaEquipment = operatingRoom.getHasAnesthesiaEquipment();
        this.hasSurgicalLights = operatingRoom.getHasSurgicalLights();
        this.hasMonitoringSystems = operatingRoom.getHasMonitoringSystems();
        this.hasOxygenSupply = operatingRoom.getHasOxygenSupply();
        this.hasXRayCapability = operatingRoom.getHasXRayCapability();
        this.hasLaserEquipment = operatingRoom.getHasLaserEquipment();
        this.hasRoboticsSystem = operatingRoom.getHasRoboticsSystem();
        this.specialFeatures = operatingRoom.getSpecialFeatures();
        this.status = operatingRoom.getStatus();
        this.lastMaintenanceDate = operatingRoom.getLastMaintenanceDate();
        this.nextMaintenanceDate = operatingRoom.getNextMaintenanceDate();
        this.businessId = operatingRoom.getBusinessId();
        this.createdAt = operatingRoom.getCreatedAt();
        this.updatedAt = operatingRoom.getUpdatedAt();
        this.createdBy = operatingRoom.getCreatedBy();
        this.updatedBy = operatingRoom.getUpdatedBy();
    }
}