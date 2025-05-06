package com.kynsoft.rrhh.infrastructure.services.rabbitMQ.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NurseRabbitMqDto {
    private UUID id;
    private String identification;
    private String name;
    private String lastName;
    private String registerNumber;
    private String status;
    private String image;
}