package com.kynsof.patients.application.query.patients.getall;


import com.kynsof.patients.domain.dto.PatientDto;
import com.kynsof.patients.domain.dto.enumTye.GenderType;
import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class PatientsResponse implements IResponse {
    private UUID id;

    private String identification;
    private String name;
    private String lastName;
    private GenderType gender;
    private Boolean hasDisability;
    private Boolean isPregnant;
    private String image;
    private String email;
    private String phone;
    private String profession;
    private String skinColor;

    public PatientsResponse(PatientDto patients) {
        this.id = patients.getId();
        this.identification = patients.getIdentification();
        this.name = patients.getName();
        this.lastName = patients.getLastName();
        this.gender = patients.getGender();
        this.hasDisability = patients.getHasDisability();
        this.isPregnant = patients.getIsPregnant();
        this.image = patients.getPhoto();
        this.email = patients.getContactInfo() != null ? patients.getContactInfo().getEmail() :"";
        this.phone =patients.getContactInfo() != null ? patients.getContactInfo().getTelephone() : "";
        this.profession = patients.getProfession();
        this.skinColor = patients.getSkinColor();
    }

}