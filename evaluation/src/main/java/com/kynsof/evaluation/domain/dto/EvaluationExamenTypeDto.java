package com.kynsof.evaluation.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class EvaluationExamenTypeDto {

    private UUID id;
    private String name;
}
