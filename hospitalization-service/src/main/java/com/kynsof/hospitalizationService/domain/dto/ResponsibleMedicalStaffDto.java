package com.kynsof.hospitalizationService.domain.dto;

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
public class ResponsibleMedicalStaffDto {
    private UUID id;
    private EmergencyCaseDto emergencyCase;
    private String firstName;
    private String lastName;
    private String identificationNumber;
    private LocalDate dateOfRecord;
    private String signature;
    private String seal;
}
