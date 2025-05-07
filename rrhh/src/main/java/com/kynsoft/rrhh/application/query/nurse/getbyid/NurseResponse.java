package com.kynsoft.rrhh.application.query.nurse.getbyid;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.rrhh.domain.dto.NurseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class NurseResponse implements IResponse {
    private UUID id;
    private String identification;
    private String code;
    private String email;
    private String name;
    private String lastName;
    private String status;
    private String registerNumber;
    private String language;
    private boolean isExpress;
    private String clasificacion; // Campo añadido para almacenar la clasificación de la enfermera
    private String phoneNumber;
    private String image;

    public NurseResponse(NurseDto nurseDto) {
        this.id = nurseDto.getId();
        this.identification = nurseDto.getIdentification();
        this.code = nurseDto.getCode();
        this.email = nurseDto.getEmail();
        this.name = nurseDto.getName();
        this.lastName = nurseDto.getLastName();
        this.status = nurseDto.getStatus();
        this.registerNumber = nurseDto.getRegisterNumber();
        this.language = nurseDto.getLanguage();
        this.isExpress = nurseDto.isExpress();
        this.clasificacion = nurseDto.getClasificacion();
        this.phoneNumber = nurseDto.getPhoneNumber();
        this.image = nurseDto.getImage();
    }
}