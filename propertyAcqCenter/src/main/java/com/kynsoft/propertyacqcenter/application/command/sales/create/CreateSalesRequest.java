package com.kynsoft.propertyacqcenter.application.command.sales.create;

import com.kynsoft.propertyacqcenter.domain.enums.PropertysAnnualValueIncrease;
import com.kynsoft.propertyacqcenter.domain.enums.PropertysStarting;
import com.kynsoft.propertyacqcenter.domain.enums.TypeOfSalesCost;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateSalesRequest {

    private String property;
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
    private Double afterRepairValue;
}
