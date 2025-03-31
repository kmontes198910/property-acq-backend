package com.kynsof.hospitalizationService.infrastructure.entity;

import com.kynsof.hospitalizationService.domain.dto.DiagnosisDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "diagnosis")
public class Diagnosis {
    
    @Id
    @Column(name="id")
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "emergency_case_id", nullable = false)
    private EmergencyCase emergencyCase;

    private String code;
    private String diagnosisType;
    private String diagnosisDescription;

    public Diagnosis(DiagnosisDto dto) {
        this.id = dto.getId();
        this.emergencyCase = dto.getEmergencyCase() != null ? new EmergencyCase(dto.getEmergencyCase()) : null;
        this.diagnosisType = dto.getDiagnosisType();
        this.diagnosisDescription = dto.getDiagnosisDescription();
        this.code = dto.getCode();
    }

    public DiagnosisDto toAggregate() {
        return new DiagnosisDto(id, emergencyCase.toAggregate(), diagnosisType, diagnosisDescription, code);
    }
    
}
