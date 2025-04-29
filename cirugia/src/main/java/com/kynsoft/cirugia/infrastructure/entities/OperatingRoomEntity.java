package com.kynsoft.cirugia.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "operating_room")
@Builder
@NoArgsConstructor
@Getter
@Setter
public class OperatingRoomEntity {

    @Id
    private UUID id;

    @Column(name = "room_number", nullable = false)
    private String roomNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;
    
    @Column(name = "floor")
    private String floor;
    
    @Column(name = "wing")
    private String wing;
    
    @Column(name = "type")
    private String type;
    
    @Column(name = "size")
    private Integer size;
    
    @Column(name = "capacity")
    private Integer capacity;
    
    @Column(name = "has_ventilation_system")
    private Boolean hasVentilationSystem;
    
    @Column(name = "has_anesthesia_equipment")
    private Boolean hasAnesthesiaEquipment;
    
    @Column(name = "has_surgical_lights")
    private Boolean hasSurgicalLights;
    
    @Column(name = "has_monitoring_systems")
    private Boolean hasMonitoringSystems;
    
    @Column(name = "has_oxygen_supply")
    private Boolean hasOxygenSupply;
    
    @Column(name = "has_xray_capability")
    private Boolean hasXRayCapability;
    
    @Column(name = "has_laser_equipment")
    private Boolean hasLaserEquipment;
    
    @Column(name = "has_robotics_system")
    private Boolean hasRoboticsSystem;
    
    @Column(name = "special_features", columnDefinition = "TEXT")
    private String specialFeatures;
    
    @Column(name = "status", nullable = false)
    private String status;
    
    @Column(name = "last_maintenance_date")
    private LocalDateTime lastMaintenanceDate;
    
    @Column(name = "next_maintenance_date")
    private LocalDateTime nextMaintenanceDate;
    
    @Column(name = "business_id", nullable = false)
    private UUID businessId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Business business;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    @Column(name = "created_by")
    private UUID createdBy;
    
    @Column(name = "updated_by")
    private UUID updatedBy;
    
    public OperatingRoomEntity(UUID id, String roomNumber, String name, String location, String floor, 
                             String wing, String type, Integer size, Integer capacity, 
                             Boolean hasVentilationSystem, Boolean hasAnesthesiaEquipment, 
                             Boolean hasSurgicalLights, Boolean hasMonitoringSystems, 
                             Boolean hasOxygenSupply, Boolean hasXRayCapability, 
                             Boolean hasLaserEquipment, Boolean hasRoboticsSystem, 
                             String specialFeatures, String status, 
                             LocalDateTime lastMaintenanceDate, LocalDateTime nextMaintenanceDate, 
                             UUID businessId, Business business, LocalDateTime createdAt, LocalDateTime updatedAt, 
                             UUID createdBy, UUID updatedBy) {
        this.id = id;
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
        this.business = business;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.createdBy = createdBy;
        this.updatedBy = updatedBy;
    }
    
    public OperatingRoomEntity(UUID id, String roomNumber, String name, String location, String floor, 
                             String wing, String type, Integer size, Integer capacity, 
                             Boolean hasVentilationSystem, Boolean hasAnesthesiaEquipment, 
                             Boolean hasSurgicalLights, Boolean hasMonitoringSystems, 
                             Boolean hasOxygenSupply, Boolean hasXRayCapability, 
                             Boolean hasLaserEquipment, Boolean hasRoboticsSystem, 
                             String specialFeatures, String status, 
                             LocalDateTime lastMaintenanceDate, LocalDateTime nextMaintenanceDate, 
                             UUID businessId, LocalDateTime createdAt, LocalDateTime updatedAt, 
                             UUID createdBy, UUID updatedBy) {
        this(id, roomNumber, name, location, floor, wing, type, size, capacity, 
             hasVentilationSystem, hasAnesthesiaEquipment, hasSurgicalLights, 
             hasMonitoringSystems, hasOxygenSupply, hasXRayCapability, 
             hasLaserEquipment, hasRoboticsSystem, specialFeatures, status, 
             lastMaintenanceDate, nextMaintenanceDate, businessId, null, 
             createdAt, updatedAt, createdBy, updatedBy);
    }
}