package com.kynsof.evaluation.domain.dto;

import com.kynsof.evaluation.domain.dto.enumDto.Status;
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
    private Status status;
    private LocalDate birthDate;

    public PatientDto(UUID id, String identificationNumber, String firstName, String lastName, Status status, LocalDate birthDate) {
        this.id = id;
        this.identification = identificationNumber;
        this.name = firstName;
        this.lastName = lastName;
        this.status = status;
        this.birthDate = birthDate;
    }
}
