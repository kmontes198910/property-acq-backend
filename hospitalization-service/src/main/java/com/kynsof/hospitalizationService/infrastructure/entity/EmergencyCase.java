package com.kynsof.hospitalizationService.infrastructure.entity;

import com.kynsof.hospitalizationService.domain.dto.EmergencyCaseDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "emergency_case")
@DynamicUpdate
public class EmergencyCase {
    @Id
    @Column(name="id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patients patient;

    private LocalDate admissionDate;
    private LocalTime admissionTime;
    private String admissionType;
    private String status;

    @OneToMany(mappedBy = "emergencyCase", fetch = FetchType.LAZY)
    private List<EmergencyCaseBed> beds;

    public EmergencyCase(EmergencyCaseDto dto) {
        this.id = dto.getId();
        this.patient = dto.getPatient() != null ? new Patients(dto.getPatient()) : null;
        this.admissionDate = dto.getAdmissionDate();
        this.admissionTime = dto.getAdmissionTime();
        this.admissionType = dto.getAdmissionType();
        this.status = dto.getStatus();
    }

    public EmergencyCaseDto toAggregate() {
        return new EmergencyCaseDto(id, patient.toAggregate(), admissionDate, admissionTime, admissionType, status);
    }
}
