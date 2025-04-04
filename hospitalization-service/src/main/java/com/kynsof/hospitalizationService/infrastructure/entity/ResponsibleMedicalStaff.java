package com.kynsof.hospitalizationService.infrastructure.entity;

import com.kynsof.hospitalizationService.domain.dto.ResponsibleMedicalStaffDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
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
@Table(name = "responsible_medical_staff")
public class ResponsibleMedicalStaff {
    @Id
    @Column(name="id")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emergency_case_id", nullable = false)
    private EmergencyCase emergencyCase;

    private String firstName;
    private String lastName;
    private String identificationNumber;
    private LocalDate dateOfRecord;
    private String signature;
    private String seal;

    public ResponsibleMedicalStaff(ResponsibleMedicalStaffDto dto) {
        this.id = dto.getId();
        this.emergencyCase = dto.getEmergencyCase() != null ? new EmergencyCase(dto.getEmergencyCase()) : null;
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
        this.identificationNumber = dto.getIdentificationNumber();
        this.dateOfRecord = dto.getDateOfRecord();
        this.signature = dto.getSignature();
        this.seal = dto.getSeal();
    }

    public ResponsibleMedicalStaffDto toAggregate() {
        return new ResponsibleMedicalStaffDto(id, emergencyCase.toAggregate(), firstName, lastName, identificationNumber, dateOfRecord, signature, seal);
    }

}
