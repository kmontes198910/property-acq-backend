package com.kynsof.treatments.application.command.exam.create;

import com.kynsof.treatments.domain.dto.enumDto.MedicalExamCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
public class CreateExamRequest {
    private String name;
    private String description;
    private MedicalExamCategory type;
    private String code;
    private UUID externalConsultationId;
}
