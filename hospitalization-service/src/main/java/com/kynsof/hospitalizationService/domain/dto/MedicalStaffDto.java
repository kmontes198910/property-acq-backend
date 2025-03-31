package com.kynsof.hospitalizationService.domain.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MedicalStaffDto {
    private UUID id;
    private String firstName;
    private String lastName;
    private String specialty;
    private String licenseNumber;
}
