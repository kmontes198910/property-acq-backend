package com.kynsoft.propertyacqcenter.infrastructure.services.rabbitMQ.dto;

import com.kynsoft.propertyacqcenter.domain.dto.BusinessDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RabbitMqEmployeeDto implements Serializable {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private UUID business;
}
