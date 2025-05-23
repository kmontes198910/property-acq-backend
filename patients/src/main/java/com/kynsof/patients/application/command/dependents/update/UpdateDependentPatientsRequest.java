package com.kynsof.patients.application.command.dependents.update;

import com.kynsof.patients.application.command.dependents.create.request.CreateDependentContactInfoRequest;
import com.kynsof.patients.domain.dto.enumTye.DisabilityType;
import com.kynsof.patients.domain.dto.enumTye.FamilyRelationship;
import com.kynsof.patients.domain.dto.enumTye.GenderType;
import com.kynsof.patients.infrastructure.entity.IdentificationType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class UpdateDependentPatientsRequest {

    private UUID primeId;
    private IdentificationType identificationType;
    private String identification;
    private String name;
    private String lastName;
    private GenderType gender;
    private Double weight;
    private Double height;
    private Boolean hasDisability;
    private Boolean isPregnant;
    private LocalDate birthDate;
    private FamilyRelationship familyRelationship;
    private String image;
    private int gestationTime;
    private CreateDependentContactInfoRequest contactInfo;
    private DisabilityType disabilityType;
}
