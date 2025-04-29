package com.kynsoft.cirugia.domain.dto;

import lombok.*;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DoctorDto {

    private UUID id;
    private String name;
    private String lastName;
    private String identification;
    private String registerNumber;
}
