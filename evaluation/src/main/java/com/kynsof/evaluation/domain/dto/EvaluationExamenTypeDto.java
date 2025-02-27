package com.kynsof.evaluation.domain.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class EvaluationExamenTypeDto {
    private UUID id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public EvaluationExamenTypeDto(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

}
