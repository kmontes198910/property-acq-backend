package com.kynsoft.cirugia.infrastructure.services.rabbitMQ.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RabbitMQPatientDeleteDto {
    private UUID id;
    private String identification;
}
