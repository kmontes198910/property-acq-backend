package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.MortgageDto;
import com.kynsoft.propertyacqcenter.domain.enums.MortgageExtraPaymentFrequency;
import com.kynsoft.propertyacqcenter.domain.enums.MortgageFrequencyInterestCompounded;
import com.kynsoft.propertyacqcenter.domain.enums.MortgageLifetimeRateCap;
import com.kynsoft.propertyacqcenter.domain.enums.MortgageType;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "mortgage_info")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Mortgage implements Serializable {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private Property property;

    @Enumerated(EnumType.STRING)
    private MortgageType mortgageType;//

    @Enumerated(EnumType.STRING)
    private MortgageLifetimeRateCap lifetimeRateCap;//

    @Enumerated(EnumType.STRING)
    private MortgageExtraPaymentFrequency extraPaymentFrequency;//

    private Integer fixedRateTermYears;
    private Integer fixedRateTermMonths;
    private Double fixedMortgageRatePercentage;

    @Enumerated(EnumType.STRING)
    private MortgageFrequencyInterestCompounded compoundFrequency;

    @Column(name = "balloon", nullable = true)
    private Boolean balloonPayment;

    @Column(name = "adjustable_rate", nullable = true)
    private Boolean adjustableRateDetails;

    private Integer paymentCuantity;

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

    public Mortgage(MortgageDto dto) {
        this.id = dto.getId();
        this.purchasePrice = dto.getPurchasePrice();
        this.loanTermYears = dto.getLoanTermYears();
        this.interestRate = dto.getInterestRate();
        this.loanStartDate = dto.getLoanStartDate();
        this.firstPaymentDate = dto.getFirstPaymentDate();
        this.extraPayments = dto.getExtraPayments();
        this.extraPaymentAmount = dto.getExtraPaymentAmount();

        this.extraPaymentFrequency = dto.getExtraPaymentFrequency();
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
        this.mortgageType = dto.getMortgageType();
        this.downPayment = dto.getDownPayment();
        this.fixedRateTermYears = dto.getFixedRateTermYears();
        this.fixedMortgageRatePercentage = dto.getFixedMortgageRatePercentage();
        this.compoundFrequency = dto.getCompoundFrequency();
        this.balloonPayment = dto.getBalloonPayment();
        this.adjustableRateDetails = dto.getAdjustableRateDetails();
        this.property = dto.getProperty() != null ? new Property(dto.getProperty()) : null;
        this.paymentCuantity = dto.getPaymentCuantity();
        this.fixedRateTermMonths = dto.getFixedRateTermMonths();
        this.lifetimeRateCap = dto.getLifetimeRateCap();
    }

    public MortgageDto toAggregate() {
        return MortgageDto.builder()
                .id(this.id)
                .adjustableRateType(adjustableRateType)
                .extraPaymentFrequency(extraPaymentFrequency)
                .hybridArmType(hybridArmType)
                .fixedRateTerm(fixedRateTerm)
                .rateChangeInterval(rateChangeInterval)
                .expectedRateChange(expectedRateChange)
                .limitRate(limitRate)
                .limitIncrease(limitIncrease)
                .howManyPayments(howManyPayments)
                .accelerationWeeklyPayments(accelerationWeeklyPayments)
                .accelerationExtraPayments(accelerationExtraPayments)
                .mortgageType(mortgageType)
                .downPayment(downPayment)
                .fixedRateTermYears(fixedRateTermYears)
                .fixedRateTermMonths(fixedRateTermMonths)
                .fixedMortgageRatePercentage(fixedMortgageRatePercentage)
                .compoundFrequency(compoundFrequency)
                .balloonPayment(balloonPayment)
                .adjustableRateDetails(adjustableRateDetails)
                .property(property.toAggregateBasic())
                .paymentCuantity(paymentCuantity)
                .lifetimeRateCap(lifetimeRateCap)

                .purchasePrice(purchasePrice)
                .loanTermYears(loanTermYears)
                .interestRate(interestRate)
                .loanStartDate(loanStartDate)
                .firstPaymentDate(firstPaymentDate)
                .extraPayments(extraPayments)
                .extraPaymentAmount(extraPaymentAmount)
                .build();
    }
}