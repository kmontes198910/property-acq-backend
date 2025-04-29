package com.kynsoft.cirugia.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.cirugia.domain.dto.DoctorDto;
import lombok.*;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DoctorResponse implements IResponse {

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

}
