package com.kynsoft.invoiceservice.infrastructure.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidad que representa los tipos de impuestos en el sistema de facturación (retención, IVA, ICE)
 */
@Entity
@Table(name = "taxes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Tax {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    /**
     * Código único del impuesto
     */
    @Column(nullable = false, unique = true)
    private String code;
    
    /**
     * Nombre descriptivo del impuesto
     */
    @Column(nullable = false)
    private String name;
    
    /**
     * Descripción detallada del impuesto
     */
    private String description;
    
    /**
     * Valor del impuesto (porcentaje)
     */
    @Column(nullable = false, precision = 5, scale = 2)
    private BigDecimal value;
    
    /**
     * Tipo de impuesto (RETENCION, IVA, ICE)
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "tax_type", nullable = false)
    private TaxType taxType;
    
    /**
     * Indica si el impuesto está activo
     */
    @Column(nullable = false)
    private Boolean status;
    
    /**
     * Indica si el valor del impuesto está predeterminado por el sistema
     */
    @Column(name = "is_predetermined")
    private Boolean isPredetermined;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private UUID createdBy;

    @Column(name = "updated_by")
    private UUID updatedBy;
    
    /**
     * Enumeración para los tipos de impuestos
     */
    public enum TaxType {
        RETENCION, 
        IVA, 
        ICE
    }
}
