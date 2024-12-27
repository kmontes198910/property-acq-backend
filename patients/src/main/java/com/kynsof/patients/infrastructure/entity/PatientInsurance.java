package com.kynsof.patients.infrastructure.entity;

import com.kynsof.patients.domain.dto.PatientInsuranceDto;
import com.kynsof.patients.domain.dto.enumTye.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class PatientInsurance {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patients patient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "insurance_id", nullable = false)
    private Insurance insurance;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private Status status;



    public PatientInsurance(Patients patient, Insurance insurance) {
        this.patient = patient;
        this.insurance = insurance;
    }

    public PatientInsuranceDto toAggregate() {
        PatientInsuranceDto patientInsuranceDto = new PatientInsuranceDto();
        patientInsuranceDto.setId(this.id);
        patientInsuranceDto.setPatientId(this.patient.getId());
        patientInsuranceDto.setInsuranceId(this.insurance.getId());
        patientInsuranceDto.setStatus(this.status);
        patientInsuranceDto.setCreated(this.createdAt);
        patientInsuranceDto.setPatient(this.patient.toAggregate());
        patientInsuranceDto.setInsurance(this.insurance.toAggregate());
        patientInsuranceDto.setUpdateAt(updatedAt);
        return patientInsuranceDto;

    }
}