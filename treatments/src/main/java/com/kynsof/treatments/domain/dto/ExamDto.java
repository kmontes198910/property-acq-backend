package com.kynsof.treatments.domain.dto;

import com.kynsof.treatments.domain.dto.enumDto.MedicalExamCategory;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExamDto {
    private UUID id;
    private String name;
    private String description;
    private MedicalExamCategory type;
    private String result;
    private Date datePerformed;
    private String code;
    private ExternalConsultationDto externalConsultation;

    public ExamDto(UUID id, String name, String description, MedicalExamCategory type, String code, ExternalConsultationDto externalConsultation) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.code = code;
        this.externalConsultation = externalConsultation;
    }

    public ExamDto(UUID id, String name, String description, MedicalExamCategory type, String code) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.code = code;
    }

}
