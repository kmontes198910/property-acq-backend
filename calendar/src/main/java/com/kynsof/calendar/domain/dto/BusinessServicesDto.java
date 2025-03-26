package com.kynsof.calendar.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BusinessServicesDto implements Serializable {
    private UUID id;
    private BusinessDto business;
    private ServiceDto service;
    private Double price;
    private LocalDateTime createdAt;

}
