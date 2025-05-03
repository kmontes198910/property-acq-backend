package com.kynsoft.propertyacqcenter.infrastructure.services.http.property.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SaleHistoryDto {
    private String event;
    private String date;//LocalDateTime
    private double price;
}
