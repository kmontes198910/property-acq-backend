package com.kynsof.treatments.infrastructure.entity;

import com.kynsof.treatments.domain.dto.PatientDto;
import com.kynsof.treatments.domain.dto.enumDto.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
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

    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDate birthDate;

    @OneToMany(mappedBy = "patient")
    private List<ExternalConsultation> externalConsultations;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PatientVaccine> patientVaccines;
    public Patients(PatientDto patients) {
        this.id = patients.getId();
        this.identification = patients.getIdentification();
        this.name = patients.getName();
        this.lastName = patients.getLastName();
        this.gender = patients.getGender();
        this.status = patients.getStatus();
        this.birthDate = patients.getBirthDate();
    }

    public PatientDto toAggregate() {
        return new PatientDto(id, identification, name, lastName, gender, status,birthDate);
    }
}
