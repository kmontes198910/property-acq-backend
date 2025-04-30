package com.kynsoft.propertyacqcenter.domain.dto.property;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PropertyTaxDto {
    private int year;
    private double total;
}
