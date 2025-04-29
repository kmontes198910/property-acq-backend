package com.kynsoft.cirugia.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "doctors")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DoctorEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "last_name", nullable = false)
    private String lastName;
    
    @Column(name = "identification", nullable = false, unique = true)
    private String identification;
    
    @Column(name = "register_number", nullable = false, unique = true)
    private String registerNumber;
    
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    
}