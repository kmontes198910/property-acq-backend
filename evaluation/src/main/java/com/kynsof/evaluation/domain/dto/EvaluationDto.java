package com.kynsof.evaluation.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

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
    private BusinessDto business;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public EvaluationDto(UUID id, PatientDto patient, String consultationReason, String medicalHistory, String physicalExam, String observation, DoctorDto doctor, BusinessDto business) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.consultationReason = consultationReason;
        this.medicalHistory = medicalHistory;
        this.physicalExam = physicalExam;
        this.observation = observation;
        this.business = business;
    }

}