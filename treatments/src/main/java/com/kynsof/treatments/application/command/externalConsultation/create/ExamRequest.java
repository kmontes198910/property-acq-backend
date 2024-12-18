package com.kynsof.treatments.application.command.externalConsultation.create;

import com.kynsof.treatments.domain.dto.enumDto.MedicalExamCategory;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExamRequest {
    private String name;
    private String description;
    private MedicalExamCategory type;
    private String code;
}
