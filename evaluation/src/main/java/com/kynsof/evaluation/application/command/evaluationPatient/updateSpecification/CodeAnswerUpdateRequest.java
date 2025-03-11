package com.kynsof.evaluation.application.command.evaluationPatient.updateSpecification;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CodeAnswerUpdateRequest {
    private String code;
    private String response;

}
