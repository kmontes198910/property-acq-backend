package com.kynsof.identity.infrastructure.services.rabbitMq.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusinessRabbitMQDto {
    private UUID id;
    private String name;
    private String latitude;
    private String longitude;
    private String logo;
    private String ruc;
    private String address;
    private String email;
    private String phone;
}
