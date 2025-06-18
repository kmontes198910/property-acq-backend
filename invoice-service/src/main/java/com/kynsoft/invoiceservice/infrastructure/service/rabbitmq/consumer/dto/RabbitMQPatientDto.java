package com.kynsoft.invoiceservice.infrastructure.service.rabbitmq.consumer.dto;

import com.kynsoft.invoiceservice.infrastructure.entities.IdentificationType;
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
public class RabbitMQPatientDto implements Serializable {
    private UUID id;
    private IdentificationType identificationType;
    private String identification;
    private String email;
    private String name;
    private String lastName;
    private String image;
    private String profession;
    private String status;
    private String address;
    private String telephone;
}
