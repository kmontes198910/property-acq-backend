package com.kynsof.hospitalizationService.infrastructure.entity;

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
}
