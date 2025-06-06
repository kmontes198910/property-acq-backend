package com.kynsoft.settings.domain.dto;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DoctorDto implements Serializable {

    private UUID id;
    private String name;
    private String lastName;
    private String identification;
    private String registerNumber;
}
