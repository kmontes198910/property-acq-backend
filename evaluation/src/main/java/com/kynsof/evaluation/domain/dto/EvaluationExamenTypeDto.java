package com.kynsof.evaluation.domain.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.UUID;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class EvaluationExamenTypeDto {

    private UUID id;
    private String name;
}
