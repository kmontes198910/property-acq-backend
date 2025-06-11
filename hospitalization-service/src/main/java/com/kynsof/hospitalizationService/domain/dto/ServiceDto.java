package com.kynsof.hospitalizationService.domain.dto;

import com.kynsof.hospitalizationService.domain.enums.EServiceStatus;
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
public class ServiceDto implements Serializable {
    private UUID id;
    private ServiceTypeDto type;
    private EServiceStatus status;
    private String picture;
    private String name;
    private Double normalAppointmentPrice;
    private String description;
    private Boolean applyIva;
    private int estimatedDuration;
    private String code;
}