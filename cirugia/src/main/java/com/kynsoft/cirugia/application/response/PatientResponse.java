package com.kynsoft.cirugia.application.response;

import com.kynsoft.cirugia.domain.dto.PatientDto;
import lombok.*;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PatientResponse {

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

}
