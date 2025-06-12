package com.kynsoft.propertyacqcenter.domain.dto;

import com.kynsoft.propertyacqcenter.domain.enums.PropertysAnnualValueIncrease;
import com.kynsoft.propertyacqcenter.domain.enums.PropertysStarting;
import com.kynsoft.propertyacqcenter.domain.enums.TypeOfSalesCost;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class SalesPropertyDto implements Serializable {

    private UUID id;
    private PropertyDto property;

    private Double capitalGainsTaxRate;
    private Double stateIncomeTaxRate;
    private Double federalIncomeTaxRate;
    private Double purchesePrice;
    private Double marketValueIndreaseRate;
    private Boolean deprecationNone;
    private Boolean deprecationStraightline;
    private Boolean deprecationDoubleDecliningBalance;

    private PropertysStarting propertysStarting;
    private PropertysAnnualValueIncrease propertysAnnualValueIncrease;
    private TypeOfSalesCost typeOfSalesCost;
}
