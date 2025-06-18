package com.kynsof.patients.infrastructure.services.rabbitMQ.dto;



import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RabbitMQPatientDeleteDto implements Serializable {
    private UUID id;
    private String identification;
}
