package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.MortgageDto;
import com.kynsoft.propertyacqcenter.domain.enums.MortgageExtraPaymentFrequency;
import com.kynsoft.propertyacqcenter.domain.enums.MortgageFrequencyInterestCompounded;
import com.kynsoft.propertyacqcenter.domain.enums.MortgageLifetimeRateCap;
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

    private MortgageType mortgageType;
    private MortgageLifetimeRateCap lifetimeRateCap;//
    private Double mortgageAmount;
    private Double downPayment;
    private Integer fixedRateTermYears;
    private Double fixedMortgageRatePercentage;
    private LocalDate firstPaymentDate;
    private MortgageFrequencyInterestCompounded compoundFrequency;
    private Boolean balloonPayment;
    private Boolean adjustableRateDetails;
    private Integer fixedRateTermMonths;

    private String adjustableRateType;
    private String hybridArmType;
    private Double fixedRateTerm;
    private Double rateChangeInterval;
    private Double expectedRateChange;
    private Double limitRate;
    private Double limitIncrease;

    private Double howManyPayments;

    private Boolean accelerationWeeklyPayments;
    private Boolean accelerationExtraPayments;

    private MortgageExtraPaymentFrequency extraPaymentFrequency;//
    private Double extraPaymentAmount;//

    public MortgageResponse(MortgageDto dto) {
        this.id = dto.getId();
        this.extraPaymentFrequency = dto.getExtraPaymentFrequency();
        this.extraPaymentAmount = dto.getExtraPaymentAmount();
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
        this.fixedRateTermMonths = dto.getFixedRateTermMonths();
        this.adjustableRateType = dto.getAdjustableRateType();
        this.hybridArmType = dto.getHybridArmType();
        this.fixedRateTerm = dto.getFixedRateTerm();
        this.rateChangeInterval = dto.getRateChangeInterval();
        this.expectedRateChange = dto.getExpectedRateChange();
        this.limitRate = dto.getLimitRate();
        this.limitIncrease = dto.getLimitIncrease();
        this.howManyPayments = dto.getHowManyPayments();
        this.accelerationWeeklyPayments = dto.getAccelerationWeeklyPayments();
        this.accelerationExtraPayments = dto.getAccelerationExtraPayments();
        this.lifetimeRateCap = dto.getLifetimeRateCap();
    }

}
