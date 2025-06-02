package com.kynsoft.cirugia.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.cirugia.domain.dto.DoctorDto;
import com.kynsoft.cirugia.infrastructure.entities.Doctor;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DoctorResponse implements IResponse, Serializable {

    private UUID id;
    private String name;
    private String lastName;
    private String identification;
    private String registerNumber;

    public DoctorResponse(DoctorDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.lastName = dto.getLastName();
        this.identification = dto.getIdentification();
        this.registerNumber = dto.getRegisterNumber();
    }


    public DoctorResponse(Doctor doctor) {
        this.id = doctor.getId();
        this.name = doctor.getName();
        this.lastName = doctor.getLastName();
        this.identification = doctor.getIdentification();
        this.registerNumber = doctor.getRegisterNumber();
    }
}
