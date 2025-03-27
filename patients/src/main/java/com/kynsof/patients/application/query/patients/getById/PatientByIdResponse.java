package com.kynsof.patients.application.query.patients.getById;


import com.kynsof.patients.application.query.contactInfo.getall.ContactInfoResponse;
import com.kynsof.patients.domain.dto.PatientByIdDto;
import com.kynsof.patients.domain.dto.enumTye.DisabilityType;
import com.kynsof.patients.domain.dto.enumTye.FamilyRelationship;
import com.kynsof.patients.domain.dto.enumTye.GenderType;
import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class PatientByIdResponse implements IResponse {
    private UUID id;

    private String identification;
    private String name;
    private String lastName;
    private GenderType gender;
    private Boolean hasDisability;
    private Boolean isPregnant;
    private String image;
    private int gestationTime = 0;
    private DisabilityType disabilityType;
    private ContactInfoResponse contactInfo;
    private FamilyRelationship familyRelationship;
    private String profession;
    private String educationalLevel;
    private int clinicalHistoryNumber;

    public PatientByIdResponse(PatientByIdDto patients) {
        this.id = patients.getId();
        this.identification = patients.getIdentification();
        this.name = patients.getName();
        this.lastName = patients.getLastName();
        this.gender = patients.getGender();
        this.hasDisability = patients.getHasDisability();
        this.isPregnant = patients.getIsPregnant();
        image = patients.getPhoto();
        disabilityType = patients.getDisabilityType();
        gestationTime = patients.getGestationTime();
        familyRelationship = patients.getFamilyRelationship();
        this.profession = patients.getProfession();
        this.educationalLevel = patients.getEducationalLevel();
        this.clinicalHistoryNumber = patients.getClinicalHistoryNumber();
        if (patients.getContactInfoDto() != null) {
            this.contactInfo = new ContactInfoResponse(patients.getContactInfoDto());
        }
    }

}