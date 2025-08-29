package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.AmortizationScheduleDto;
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
    private Integer fixedRateTermYears;
    private Double fixedMortgageRatePercentage;
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

    // ✅ ESENCIALES (mantener)
    private Double purchasePrice;        // Precio de compra ($)
    private Double downPayment;          // Pago inicial ($)
    private Integer loanTermYears;       // Plazo (15, 20, 30 años)
    private Double interestRate;         // Tasa de interés anual (%)
    private LocalDate loanStartDate;     // Fecha de inicio

    // ✅ OPCIONALES pero útiles
    private LocalDate firstPaymentDate;  // Fecha primer pago
    private Boolean extraPayments;       // Pagos extras
    private Double extraPaymentAmount;   // Monto pagos extras

    private AmortizationScheduleDto amortizations;

    public MortgageResponse(MortgageDto dto) {
        this.id = dto.getId();
        this.extraPaymentFrequency = dto.getExtraPaymentFrequency();
        this.property = dto.getProperty() != null ? new PropertiesBasicResponse(dto.getProperty()) : null;
        this.mortgageType = dto.getMortgageType();
        this.fixedRateTermYears = dto.getFixedRateTermYears();
        this.fixedMortgageRatePercentage = dto.getFixedMortgageRatePercentage();
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

        this.purchasePrice = dto.getPurchasePrice();
        this.downPayment = dto.getDownPayment();
        this.loanTermYears = dto.getLoanTermYears();
        this.interestRate = dto.getInterestRate();
        this.loanStartDate = dto.getLoanStartDate();
        this.firstPaymentDate = dto.getFirstPaymentDate();
        this.extraPayments = dto.getExtraPayments();
        this.extraPaymentAmount = dto.getExtraPaymentAmount();
    }

}
