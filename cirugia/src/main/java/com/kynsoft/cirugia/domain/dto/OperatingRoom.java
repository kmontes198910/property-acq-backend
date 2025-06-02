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
public class OperatingRoom implements Serializable {

    private UUID id;
    private String roomNumber;           // Número de identificación del salón quirúrgico
    private String name;                 // Nombre del salón quirúrgico (opcional)
    private String location;             // Ubicación dentro del hospital/clínica
    private String floor;                // Piso donde se encuentra
    private String wing;                 // Ala o sección del edificio
    private String type;                 // Tipo de salón (general, especializado, etc.)
    private Integer size;                // Tamaño en metros cuadrados
    private Integer capacity;            // Capacidad de personas
    private Boolean hasVentilationSystem;// Si tiene sistema de ventilación especializado
    private Boolean hasAnesthesiaEquipment;  // Si tiene equipo de anestesia
    private Boolean hasSurgicalLights;   // Si tiene luces quirúrgicas
    private Boolean hasMonitoringSystems;// Si tiene sistemas de monitoreo
    private Boolean hasOxygenSupply;     // Si tiene suministro de oxígeno
    private Boolean hasXRayCapability;   // Si tiene capacidad para rayos X
    private Boolean hasLaserEquipment;   // Si tiene equipo láser
    private Boolean hasRoboticsSystem;   // Si tiene sistema robótico para cirugías
    private String specialFeatures;      // Características especiales (texto libre)
    private String status;               // Estado (AVAILABLE, IN_USE, MAINTENANCE, RESERVED)
    private LocalDateTime lastMaintenanceDate; // Última fecha de mantenimiento
    private LocalDateTime nextMaintenanceDate; // Próxima fecha programada para mantenimiento
    private UUID businessId;             // ID del negocio/hospital/clínica
    private LocalDateTime createdAt;     // Fecha de creación del registro
    private LocalDateTime updatedAt;     // Fecha de actualización del registro
    private UUID createdBy;              // Usuario que creó el registro
    private UUID updatedBy;              // Usuario que actualizó el registro
}