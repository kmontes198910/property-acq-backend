package com.kynsoft.cirugia.domain.dto;

import lombok.*;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PatientDto {

    private UUID id;
    private String identification;
    private String email;
    private String name;
    private String lastName;
    private String image;
    private String profession;
}
