package com.kynsof.hospitalizationService.infrastructure.entity;

import com.kynsof.hospitalizationService.domain.dto.PatientDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Patients {
    @Id
    @Column(name="id")
    private UUID id;

    private String identification;

    private String name;
    private String lastName;

    private String gender;

    private String status;
    private LocalDate birthDate;
    private String profession;

    public Patients(PatientDto patients) {
        this.id = patients.getId();
        this.identification = patients.getIdentification();
        this.name = patients.getName();
        this.lastName = patients.getLastName();
        this.gender = patients.getGender();
        this.status = patients.getStatus();
        this.birthDate = patients.getBirthDate();
        this.profession = patients.getProfession();
    }

    public PatientDto toAggregate() {
        return new PatientDto(id, identification, name, lastName, gender, status,birthDate, profession);
    }
}
