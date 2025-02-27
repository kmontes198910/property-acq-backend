package com.kynsof.evaluation.infrastructure.entity;

import com.kynsof.evaluation.domain.dto.EvaluationExamenTypeDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class EvaluationExamenType {

    @Id
    private UUID id;
    private String name;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public EvaluationExamenType(EvaluationExamenTypeDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
    }

    public EvaluationExamenTypeDto toAggregate() {
        return new EvaluationExamenTypeDto(
                this.id,
                this.name,
                this.createdAt,
                this.updatedAt
        );
    }

}
