package com.kynsoft.cirugia.application.query.operatingroom;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.cirugia.domain.dto.OperatingRoom;
import com.kynsoft.cirugia.infrastructure.entities.OperatingRoomEntity;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
public class OperatingRoomSearchResponse implements IResponse, Serializable {
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

    public OperatingRoomSearchResponse(OperatingRoomEntity entity) {
        this.id = entity.getId();
        this.roomNumber = entity.getRoomNumber();
        this.name = entity.getName();
        this.location = entity.getLocation();
        this.floor = entity.getFloor();
        this.wing = entity.getWing();
        this.type = entity.getType();
        this.size = entity.getSize();
        this.capacity = entity.getCapacity();
        this.hasVentilationSystem = entity.getHasVentilationSystem();
        this.hasAnesthesiaEquipment = entity.getHasAnesthesiaEquipment();
        this.hasSurgicalLights = entity.getHasSurgicalLights();
        this.hasMonitoringSystems = entity.getHasMonitoringSystems();
        this.hasOxygenSupply = entity.getHasOxygenSupply();
        this.hasXRayCapability = entity.getHasXRayCapability();
        this.hasLaserEquipment = entity.getHasLaserEquipment();
        this.hasRoboticsSystem = entity.getHasRoboticsSystem();
        this.specialFeatures = entity.getSpecialFeatures();
        this.status = entity.getStatus();
        this.lastMaintenanceDate = entity.getLastMaintenanceDate();
        this.nextMaintenanceDate = entity.getNextMaintenanceDate();
        this.businessId = entity.getBusinessId();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
        this.createdBy = entity.getCreatedBy();
        this.updatedBy = entity.getUpdatedBy();
    }
}