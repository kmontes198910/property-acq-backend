package com.kynsoft.rrhh.infrastructure.services.rabbitMQ.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DoctorRabbitMqDto implements Serializable {

    private UUID id;
    private String identification;
    private String name;
    private String lastName;
    private String registerNumber;
    private String status;
    private String image;
}
