package com.kynsoft.medicaltest.application.query.doctor.getbyid;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.medicaltest.domain.dto.DoctorDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class DoctorResponse implements IResponse {
    private UUID id;
    private String identification;
    private String name;
    private String lastName;
    private String registerNumber;
    private String image;

    public DoctorResponse(DoctorDto dto) {
        this.id = dto.getId();
        this.identification = dto.getIdentification();
        this.name = dto.getName();
        this.lastName = dto.getLastName();
        this.registerNumber = dto.getRegisterNumber();
        this.image = dto.getImage();
    }

}
