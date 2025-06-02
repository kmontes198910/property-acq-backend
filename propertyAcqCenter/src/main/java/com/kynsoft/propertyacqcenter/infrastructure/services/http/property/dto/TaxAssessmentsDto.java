package com.kynsoft.propertyacqcenter.infrastructure.services.http.property.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaxAssessmentsDto {

    private UUID id;
    private int year;
    private int value;
    private Integer land;
    private Integer improvements;
}
