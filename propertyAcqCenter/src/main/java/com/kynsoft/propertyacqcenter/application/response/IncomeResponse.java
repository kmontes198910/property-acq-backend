package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class IncomeResponse implements Serializable, IResponse {

    private UUID id;
    private PropertiesBasicResponse property;
    private Double grossMonthlyIncome;
    private Double totalNetMonthlyIncome;
    private Double increaseRate;
    private Boolean increaseTypePercentage;
    private Boolean increaseFixedDollarAmount;

    public IncomeResponse(IncomeDto dto) {
        this.id = dto.getId();
        this.property = dto.getProperty() != null ? new PropertiesBasicResponse(dto.getProperty()) : null;
        this.grossMonthlyIncome = dto.getGrossMonthlyIncome();
        this.totalNetMonthlyIncome = dto.getTotalNetMonthlyIncome();
        this.increaseRate = dto.getIncreaseRate();
        this.increaseTypePercentage = dto.getIncreaseTypePercentage();
        this.increaseFixedDollarAmount = dto.getIncreaseFixedDollarAmount();
    }

}
