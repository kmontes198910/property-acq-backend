package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.MortgageDto;
import com.kynsoft.propertyacqcenter.domain.enums.MortgageType;
import java.time.LocalDate;
import lombok.Builder;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class MortgageResponse implements IResponse {

    private UUID id;
    private PropertiesBasicResponse property;

    private MortgageType mortgageType;//TODO: por definir
    private Double mortgageAmount;
    private Double downPayment;
    private Integer fixedRateTermYears;
    private Double fixedMortgageRatePercentage;
    private LocalDate firstPaymentDate;
    private String compoundFrequency;//TODO: por definir
    private Double balloonPayment;
    private String adjustableRateDetails;//TODO: por definir

    public MortgageResponse(MortgageDto dto) {
        this.id = dto.getId();
        this.property = dto.getProperty() != null ? new PropertiesBasicResponse(dto.getProperty()) : null;
        this.mortgageType = dto.getMortgageType();
        this.mortgageAmount = dto.getMortgageAmount();
        this.downPayment = dto.getDownPayment();
        this.fixedRateTermYears = dto.getFixedRateTermYears();
        this.fixedMortgageRatePercentage = dto.getFixedMortgageRatePercentage();
        this.firstPaymentDate = dto.getFirstPaymentDate();
        this.compoundFrequency = dto.getCompoundFrequency();
        this.balloonPayment = dto.getBalloonPayment();
        this.adjustableRateDetails = dto.getAdjustableRateDetails();
    }

}
