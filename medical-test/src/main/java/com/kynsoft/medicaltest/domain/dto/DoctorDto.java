package com.kynsoft.medicaltest.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class DoctorDto {
    private UUID id;
    private String identification;
    private String name;
    private String lastName;
    private String registerNumber;
    private String image;
}
