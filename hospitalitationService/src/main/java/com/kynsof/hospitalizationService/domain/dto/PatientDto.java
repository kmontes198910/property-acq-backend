package com.kynsof.hospitalizationService.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class PatientDto {

    private UUID id;
    private String identification;
    private String name;
    private String lastName;
    private String gender;
    private String status;
    private LocalDate birthDate;
    private String profession;

    public PatientDto(UUID id, String identificationNumber, String firstName, String lastName, String status, LocalDate birthDate, String profession) {
        this.id = id;
        this.identification = identificationNumber;
        this.name = firstName;
        this.lastName = lastName;
        this.status = status;
        this.birthDate = birthDate;
        this.profession = profession;
    }
}
