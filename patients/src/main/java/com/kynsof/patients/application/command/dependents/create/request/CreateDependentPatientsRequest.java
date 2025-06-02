package com.kynsof.patients.application.command.dependents.create.request;

import com.kynsof.patients.domain.dto.enumTye.DisabilityType;
import com.kynsof.patients.domain.dto.enumTye.FamilyRelationship;
import com.kynsof.patients.domain.dto.enumTye.GenderType;
import com.kynsof.patients.infrastructure.entity.IdentificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class CreateDependentPatientsRequest {

    private UUID primeId;
    private String image;
    private IdentificationType identificationType;
    private String identification;
    private String name;
    private String lastName;
    private GenderType gender;
    private Double weight;
    private Double height;
    private Boolean hasDisability;
    private DisabilityType disabilityType;
    private FamilyRelationship familyRelationship;
    private Boolean isPregnant;
    private int gestationTime;
    private CreateDependentContactInfoRequest contactInfo;

}
