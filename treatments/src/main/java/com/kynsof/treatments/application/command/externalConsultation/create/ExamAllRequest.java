package com.kynsof.treatments.application.command.externalConsultation.create;

import com.kynsof.treatments.domain.dto.enumDto.MedicalExamCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ExamAllRequest {
    private UUID id;
    private String name;
    private String description;
    private MedicalExamCategory type;
    private String code;
}
