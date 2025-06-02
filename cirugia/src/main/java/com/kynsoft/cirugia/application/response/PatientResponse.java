package com.kynsoft.cirugia.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.cirugia.domain.dto.PatientDto;
import com.kynsoft.cirugia.infrastructure.entities.Patient;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PatientResponse implements IResponse, Serializable {

    private UUID id;
    private String identification;
    private String email;
    private String name;
    private String lastName;
    private String image;
    private String profession;

    public PatientResponse(PatientDto dto) {
        this.id = dto.getId();
        this.identification = dto.getIdentification();
        this.email = dto.getEmail();
        this.name = dto.getName();
        this.lastName = dto.getLastName();
        this.image = dto.getImage();
        this.profession = dto.getProfession();
    }

    public PatientResponse(Patient patient) {
        this.id = patient.getId();
        this.identification = patient.getIdentification();
        this.email = patient.getEmail();
        this.name = patient.getName();
        this.lastName = patient.getLastName();
        this.image = patient.getImage();
        this.profession = patient.getProfession();
    }
}
