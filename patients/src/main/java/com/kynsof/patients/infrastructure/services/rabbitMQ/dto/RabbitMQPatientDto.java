package com.kynsof.patients.infrastructure.services.rabbitMQ.dto;

import com.kynsof.patients.infrastructure.entity.IdentificationType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.UUID;
import lombok.AllArgsConstructor;

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
