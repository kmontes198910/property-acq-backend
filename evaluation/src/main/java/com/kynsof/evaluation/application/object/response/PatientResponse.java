package com.kynsof.evaluation.application.object.response;

import com.kynsof.evaluation.domain.dto.PatientDto;
import com.kynsof.evaluation.domain.dto.enumDto.Status;
import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class PatientResponse implements IResponse {

    private UUID id;
    private String identification;
    private String name;
    private String lastName;
    private String gender;
    private Status status;
    private LocalDate birthDate;

    public PatientResponse(PatientDto dto) {
        this.id = dto.getId();
        this.identification = dto.getIdentification();
        this.name = dto.getName();
        this.lastName = dto.getLastName();
        this.status = dto.getStatus();
        this.birthDate = dto.getBirthDate();
    }
}
