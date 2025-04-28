package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.LienDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "lien_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lien implements Serializable {

    @Id
    private UUID id;

    private double lienAmount;

    @Column(columnDefinition = "boolean DEFAULT FALSE")
    private boolean involuntary;

    @Column(columnDefinition = "boolean DEFAULT FALSE")
    private boolean active;

    private String documentType;

    private String taxType;

    private LocalDateTime taxDate;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private Property property;

    public Lien(LienDto lien) {
        this.id = lien.getId();
        this.lienAmount = lien.getLienAmount();
        this.involuntary = lien.isInvoluntary();
//        this.property = lien.getProperty() != null ? new Property(lien.getProperty()) : null;
        this.active = lien.isActive();
    }

    public LienDto toAggregate() {
        return LienDto.builder()
                .id(this.id)
                .lienAmount(this.lienAmount)
                .involuntary(this.involuntary)
                .active(this.active)
                .documentType(this.documentType)
                .taxType(this.taxType)
                .taxDate(this.taxDate)
                .build();
    }
}
