package com.kynsof.patients.domain.dto;

import com.kynsof.patients.domain.dto.enumTye.DisabilityType;
import com.kynsof.patients.domain.dto.enumTye.FamilyRelationship;
import com.kynsof.patients.domain.dto.enumTye.GenderType;
import com.kynsof.patients.domain.dto.enumTye.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
public class PatientByIdDto implements Serializable {

    private UUID id;
    private String identification;
    private String name;
    private String lastName;
    private GenderType gender;
    private Status status;
    private Boolean hasDisability;
    private Boolean isPregnant;
    private String photo;
    private DisabilityType disabilityType;
    private int gestationTime;
    private FamilyRelationship familyRelationship;
    private ContactInfoDto contactInfoDto;
    private String profession;
    private String educationalLevel;
    private int clinicalHistoryNumber;
}
