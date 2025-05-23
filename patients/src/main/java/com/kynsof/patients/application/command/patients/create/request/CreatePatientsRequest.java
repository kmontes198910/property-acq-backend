package com.kynsof.patients.application.command.patients.create.request;

import com.kynsof.patients.domain.dto.enumTye.BloodType;
import com.kynsof.patients.domain.dto.enumTye.GenderType;
import com.kynsof.patients.infrastructure.entity.IdentificationType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CreatePatientsRequest {
    private UUID id;
    private IdentificationType identificationType;
    private String identification;
    private String name;
    private String lastName;
    private GenderType gender;
    private String image;
    private CreatePatientContactInfoRequest contactInfo;
    private String profession;
    private String educationalLevel;
    private BloodType bloodType;
    private String skinColor;
}
