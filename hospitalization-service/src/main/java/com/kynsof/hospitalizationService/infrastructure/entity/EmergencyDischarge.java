package com.kynsof.hospitalizationService.infrastructure.entity;

import com.kynsof.hospitalizationService.domain.dto.EmergencyDischargeDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "emergency_discharge")
public class EmergencyDischarge {
    @Id
    @Column(name="id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emergency_case_id", nullable = false)
    private EmergencyCase emergencyCase;

    private String condition;
    private Boolean hospitalizationRequired;
    private Boolean externalConsultationRequired;
    private Boolean emergencyObservationRequired;
    private Boolean referralRequired;
    private Boolean reverseReferral;
    private String observations;
    private Integer restDays;

    public EmergencyDischarge(EmergencyDischargeDto dto) {
        this.id = dto.getId();
        this.emergencyCase = dto.getEmergencyCase() != null ? new EmergencyCase(dto.getEmergencyCase()) : null;
        this.condition = dto.getCondition();
        this.hospitalizationRequired = dto.getHospitalizationRequired();
        this.externalConsultationRequired = dto.getExternalConsultationRequired();
        this.emergencyObservationRequired = dto.getEmergencyObservationRequired();
        this.referralRequired = dto.getReferralRequired();
        this.reverseReferral = dto.getReverseReferral();
        this.observations = dto.getObservations();
        this.restDays = dto.getRestDays();
    }

    public EmergencyDischargeDto toAggregate() {
        return new EmergencyDischargeDto(id, emergencyCase.toAggregate(), condition, hospitalizationRequired, externalConsultationRequired, emergencyObservationRequired, referralRequired, reverseReferral, observations, restDays);
    }

}
