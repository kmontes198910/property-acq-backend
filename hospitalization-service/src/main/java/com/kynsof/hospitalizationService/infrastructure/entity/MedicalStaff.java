package com.kynsof.hospitalizationService.infrastructure.entity;

import com.kynsof.hospitalizationService.domain.dto.MedicalStaffDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
@Table(name = "medical_staff")
public class MedicalStaff {
    @Id
    @Column(name="id")
    private UUID id;

    private String firstName;
    private String lastName;
    private String specialty;
    private String licenseNumber;

    public MedicalStaff(MedicalStaffDto dto) {
        this.id = dto.getId();
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
        this.specialty = dto.getSpecialty();
        this.licenseNumber = dto.getLicenseNumber();
    }

    public MedicalStaffDto toAggregate() {
        return new MedicalStaffDto(id, firstName, lastName, specialty, licenseNumber);
    }

}
