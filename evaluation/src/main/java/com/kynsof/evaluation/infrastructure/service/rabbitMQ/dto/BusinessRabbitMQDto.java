package com.kynsof.evaluation.infrastructure.service.rabbitMQ.dto;

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
}
