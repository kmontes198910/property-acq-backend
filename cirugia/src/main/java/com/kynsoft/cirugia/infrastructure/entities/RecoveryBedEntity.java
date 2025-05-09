package com.kynsoft.cirugia.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "recovery_beds")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RecoveryBedEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "bed_number", nullable = false)
    private String bedNumber;
    
    @Column(name = "location")
    private String location;
    
    @Column(name = "type")
    private String type;
    
    @Column(name = "status", nullable = false)
    private String status;
    
    @Column(name = "floor")
    private String floor;
    
    @Column(name = "has_monitor")
    private Boolean hasMonitor;
    
    @Column(name = "has_oxygen_supply")
    private Boolean hasOxygenSupply;
    
    @Column(name = "last_maintenance_date")
    private LocalDateTime lastMaintenanceDate;
    
    @Column(name = "recovery_room_id")
    private UUID recoveryRoomId;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
    @Column(name = "created_by")
    private UUID createdBy;
    
    @Column(name = "updated_by")
    private UUID updatedBy;
    
    @Column(name = "business_id", nullable = false)
    private UUID businessId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Business business;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recovery_room_id", referencedColumnName = "id", insertable = false, updatable = false)
    private RecoveryRoomEntity recoveryRoom;
    
    @OneToMany(mappedBy = "recoveryBed")
    private List<SurgeryEntity> surgeries;
    
    @OneToMany(mappedBy = "recoveryBed")
    private List<BedAssignmentEntity> bedAssignments;
}