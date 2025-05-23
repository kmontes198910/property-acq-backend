package com.kynsof.patients.domain.dto;

import com.kynsof.patients.domain.dto.enumTye.DisabilityType;
import com.kynsof.patients.domain.dto.enumTye.FamilyRelationship;
import com.kynsof.patients.domain.dto.enumTye.GenderType;
import com.kynsof.patients.domain.dto.enumTye.Status;
import com.kynsof.patients.infrastructure.entity.IdentificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class DependentPatientDto {
    private UUID id;
    private IdentificationType identificationType;
    private String identification;
    private String name;
    private String lastName;
    private GenderType gender;
    private Status status;
    private PatientDto prime;
    private Boolean hasDisability;
    private Boolean isPregnant;
    private FamilyRelationship familyRelationship;
    private String photo;
    private DisabilityType disabilityType;
    private int gestationTime;
}
