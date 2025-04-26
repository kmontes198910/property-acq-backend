package com.kynsoft.rrhh.application.query.doctor.getbyid;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.rrhh.domain.dto.DoctorDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class DoctorResponse implements IResponse, Serializable {
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
    private String phoneNumber;
    private String image;

    public DoctorResponse(DoctorDto userSystemDto) {
        this.id = userSystemDto.getId();
        this.email = userSystemDto.getEmail();
        this.name = userSystemDto.getName();
        this.lastName = userSystemDto.getLastName();
        this.status = userSystemDto.getStatus();
        this.registerNumber = userSystemDto.getRegisterNumber();
        this.language = userSystemDto.getLanguage();
        this.isExpress = userSystemDto.isExpress();
        this.identification = userSystemDto.getIdentification();
        this.phoneNumber = userSystemDto.getPhoneNumber();
        this.image = userSystemDto.getImage();
        this.code = userSystemDto.getCode();
    }

}