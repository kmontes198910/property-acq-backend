package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.*;
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
public class SalesPropertyResponse implements Serializable, IResponse {

    private UUID id;
    private PropertiesBasicResponse property;

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

    public SalesPropertyResponse(SalesPropertyDto dto) {
        this.id = dto.getId();
        this.property = dto.getProperty() != null ? new PropertiesBasicResponse(dto.getProperty()) : null;
        this.capitalGainsTaxRate = dto.getCapitalGainsTaxRate();
        this.stateIncomeTaxRate = dto.getStateIncomeTaxRate();
        this.federalIncomeTaxRate = dto.getFederalIncomeTaxRate();
        this.purchesePrice = dto.getPurchesePrice();
        this.marketValueIndreaseRate = dto.getMarketValueIndreaseRate();
        this.deprecationNone = dto.getDeprecationNone();
        this.deprecationStraightline = dto.getDeprecationStraightline();
        this.deprecationDoubleDecliningBalance = dto.getDeprecationDoubleDecliningBalance();
        this.propertysStarting = dto.getPropertysStarting();
        this.propertysAnnualValueIncrease = dto.getPropertysAnnualValueIncrease();
        this.typeOfSalesCost = dto.getTypeOfSalesCost();
    }

}
