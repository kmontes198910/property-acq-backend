package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.IncomeDetailsBreakdownDto;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class IncomeDetailsBreakdownResponse implements Serializable, IResponse {

    private UUID id;

    //Detail Breakdown
    private Double unitType;
    private Double quantity;
    private Double rentMo;
    private Double sqft;//Sqft
    private Double sqftValue;//$/SqFt
    private Double occupancy;
    private Double annualIncrease;

    public IncomeDetailsBreakdownResponse(IncomeDetailsBreakdownDto dto) {
        this.id = dto.getId();
        this.unitType = dto.getUnitType();
        this.quantity = dto.getQuantity();
        this.rentMo = dto.getRentMo();
        this.sqft = dto.getSqft();
        this.sqftValue = dto.getSqftValue();
        this.occupancy = dto.getOccupancy();
        this.annualIncrease = dto.getAnnualIncrease();
    }

}
