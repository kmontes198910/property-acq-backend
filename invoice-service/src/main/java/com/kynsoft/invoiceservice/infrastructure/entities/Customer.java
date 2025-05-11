package com.kynsoft.invoiceservice.infrastructure.entities;

import com.kynsoft.invoiceservice.infrastructure.entities.converters.IdentificationTypeConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "customers")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Convert(converter = IdentificationTypeConverter.class)
    @Column(name = "id_type", nullable = false, length = 2)
    private IdentificationType idType; // RUC, CEDULA, PASAPORTE, etc.
    
    @Column(name = "id_number", nullable = false)
    private String idNumber;
    
    @Column(name = "business_name", nullable = false)
    private String businessName;
    
    private String address;
    
    private String email;
    
    private String phone;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        if (isActive == null) {
            isActive = true;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}