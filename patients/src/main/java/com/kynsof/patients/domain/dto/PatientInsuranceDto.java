package com.kynsof.patients.domain.dto;

import com.kynsof.patients.domain.dto.enumTye.Status;
import com.kynsof.patients.infrastructure.entity.PatientInsurance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class PatientInsuranceDto {
    private UUID id;
    private UUID patientId;
    private UUID insuranceId;
    private Status status;
    private LocalDateTime created;
    private PatientDto patient;
    private InsuranceDto insurance;

    public PatientInsuranceDto( UUID patientId, UUID insuranceId, Status status) {
        this.patientId = patientId;
        this.insuranceId = insuranceId;
        this.status = status;
    }
}