package com.kynsof.treatments.domain.dto;

import com.kynsof.treatments.application.query.services.replicate.ServicesResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LabOrderDto {
    private UUID id;
    private String referenceNumber;
    private PatientDto patient;
    private DoctorDto doctor;
    private Date consultationTime;
    private List<ExamDto> labTests;
    private BusinessDto business;
    private ServicesResponse especialty;
    
    // Constructor que toma un ExternalConsultationDto y extrae solo los exámenes de tipo LAB_TESTS
    public LabOrderDto(ExternalConsultationDto consultationDto) {
        this.id = consultationDto.getId();
        this.referenceNumber = consultationDto.getReferenceNumber();
        this.patient = consultationDto.getPatient();
        this.doctor = consultationDto.getDoctor();
        this.consultationTime = consultationDto.getConsultationTime();
        this.business = consultationDto.getBusiness();
        this.especialty = new ServicesResponse(consultationDto.getService());
        // Filtrar solo los exámenes de tipo LAB_TESTS
        if (consultationDto.getExams() != null) {
            this.labTests = consultationDto.getExams().stream()
                    .filter(exam -> exam.getType() != null && 
                            exam.getType().equals(com.kynsof.treatments.domain.dto.enumDto.MedicalExamCategory.LAB_TESTS))
                    .toList();
        }
    }
}