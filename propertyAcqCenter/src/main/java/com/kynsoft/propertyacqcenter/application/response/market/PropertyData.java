package com.kynsoft.propertyacqcenter.application.response.market;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PropertyData {
    private String id;
    private String zipCode;
    private SaleData saleData;
    private RentalData rentalData;
}
