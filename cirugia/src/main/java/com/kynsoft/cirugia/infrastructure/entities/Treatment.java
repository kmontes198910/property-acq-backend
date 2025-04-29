package com.kynsoft.cirugia.infrastructure.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Treatment {

    @Id
    private UUID id;
    private String code;
    private String name;
    private String description;
    private int quantity;
    private String medicineUnit;
    private String status;
    @ManyToOne
    @JoinColumn(name = "surgery_id", referencedColumnName = "id", insertable = false, updatable = false)
    private SurgeryEntity surgery;

    @Column(name = "surgery_id", nullable = false)
    private UUID surgeryId;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
