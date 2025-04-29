package com.kynsoft.cirugia.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "businesses")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Business {

    @Id
    private UUID id;
    private String name;
    private String latitude;
    private String longitude;
    private String address;
    private String logo;
    private String phone;
    private String email;
    private String ruc;
    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
