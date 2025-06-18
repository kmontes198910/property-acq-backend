package com.kynsof.evaluation.infrastructure.service.rabbitMQ.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class RabbitMQPatientDeleteDto {
    private UUID id;
    private String identification;
}
