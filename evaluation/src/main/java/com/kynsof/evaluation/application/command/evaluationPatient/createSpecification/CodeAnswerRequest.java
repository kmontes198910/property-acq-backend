package com.kynsof.evaluation.application.command.evaluationPatient.createSpecification;

import com.kynsof.evaluation.domain.dto.enumDto.EvaluationExamenType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CodeAnswerRequest {
    private String code;
    private String response;

}
