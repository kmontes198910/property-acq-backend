package com.kynsoft.cirugia.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "patients")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PatientEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "identification", nullable = false, unique = true)
    private String identification;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
}