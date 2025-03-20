package com.kynsof.evaluation.application.command.evaluationPatient.createSpecification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CodeAnswerRequest {
    private String code;
    private String response;

}
