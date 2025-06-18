package com.kynsof.treatments.infrastructure.entity;

import com.kynsof.treatments.domain.dto.MedicinesDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Medicines {

    @Id
    private UUID id;
    @Column(unique = true)
    private String name;

    private String presentation;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public Medicines(MedicinesDto medicine) {
        this.id = medicine.getId();
        this.name = medicine.getName();
        this.presentation = medicine.getPresentation();
    }

    public MedicinesDto toAggregate() {
        return new MedicinesDto(this.id, this.name,
                this.presentation);
    }

}
