package com.kynsof.evaluation.infrastructure.entity;

import com.kynsof.evaluation.domain.dto.EvaluationExamenTypeDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class EvaluationExamenType {

    @Id
    private UUID id;
    private String name;

    public EvaluationExamenType(EvaluationExamenTypeDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
    }

    public EvaluationExamenTypeDto toAggregate() {
        return new EvaluationExamenTypeDto(
                this.id,
                this.name
        );
    }

}
