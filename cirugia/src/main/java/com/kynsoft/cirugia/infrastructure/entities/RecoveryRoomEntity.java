package com.kynsoft.cirugia.infrastructure.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Entidad JPA que representa una sala de recuperación que contiene múltiples camas.
 */
@Data
@Entity
@Table(name = "recovery_rooms")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecoveryRoomEntity {
    
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private UUID id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "floor")
    private String floor;
    
    @Column(name = "wing")
    private String wing;
    
    @Column(name = "capacity")
    private Integer capacity;
    
    @Column(name = "status", nullable = false)
    private String status;
    
    @Column(name = "business_id", nullable = false)
    private UUID businessId;
    
    @Column(name = "room_type")
    private String roomType;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    
    @Column(name = "additional_info", columnDefinition = "TEXT")
    private String additionalInfo;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "created_by")
    private UUID createdBy;
    
    @Column(name = "updated_by")
    private UUID updatedBy;
    
    @OneToMany(mappedBy = "recoveryRoom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private Set<RecoveryBedEntity> beds = new HashSet<>();
    
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (updatedAt == null) {
            updatedAt = createdAt;
        }
        if (isActive == null) {
            isActive = true;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    /**
     * Método auxiliar para agregar una cama a la sala y establecer correctamente la relación bidireccional
     * @param bed La cama a agregar a esta sala
     */
    public void addBed(RecoveryBedEntity bed) {
        beds.add(bed);
        bed.setRecoveryRoom(this);
    }
    
    /**
     * Método auxiliar para quitar una cama de la sala y limpiar la relación bidireccional
     * @param bed La cama a quitar de esta sala
     */
    public void removeBed(RecoveryBedEntity bed) {
        beds.remove(bed);
        bed.setRecoveryRoom(null);
    }
}