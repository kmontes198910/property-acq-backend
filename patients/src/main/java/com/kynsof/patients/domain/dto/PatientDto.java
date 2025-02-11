package com.kynsof.patients.domain.dto;

import com.kynsof.patients.domain.dto.enumTye.DisabilityType;
import com.kynsof.patients.domain.dto.enumTye.GenderType;
import com.kynsof.patients.domain.dto.enumTye.Status;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;


@Getter
@Setter
public class PatientDto implements Serializable {

    private  UUID id;
    private  String identification;
    private  String name;
    private  String lastName;
    private  GenderType gender;
    private  Status status;
    private  Boolean hasDisability;
    private  Boolean isPregnant;
    private  String photo;
    private  DisabilityType disabilityType;
    private  int gestationTime;

    private ContactInfoDto contactInfo;

    public PatientDto(UUID id, String identification, String name, String lastName, GenderType gender, Status status,
                     Boolean hasDisability, Boolean isPregnant, String photo,
                      DisabilityType disabilityType, int gestationTime) {
        this.id = id;
        this.identification = identification;
        this.name = name;
        this.lastName = lastName;
        this.gender = gender;
        this.status = status;
        this.hasDisability = hasDisability;
        this.isPregnant = isPregnant;
        this.photo = photo;
        this.disabilityType = disabilityType;
        this.gestationTime = gestationTime;
    }

    public PatientDto(UUID id, String identification, String name, String lastName, GenderType gender, Status status, String photo) {
        this.id = id;
        this.identification = identification;
        this.name = name;
        this.lastName = lastName;
        this.gender = gender;
        this.status = status;
        this.photo = photo;
    }
}
