package com.kynsof.evaluation.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
public class EvaluationDto {
    private UUID id;
    private PatientDto patient;
    private DoctorDto doctor;
    private String consultationReason;
    private String medicalHistory;
    private String physicalExam;
    private String observation;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public EvaluationDto(UUID id, PatientDto patient, String consultationReason, String medicalHistory, String physicalExam, String medicalSpeciality, DoctorDto doctor) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.consultationReason = consultationReason;
        this.medicalHistory = medicalHistory;
        this.physicalExam = physicalExam;
        this.observation = medicalSpeciality;
    }

}