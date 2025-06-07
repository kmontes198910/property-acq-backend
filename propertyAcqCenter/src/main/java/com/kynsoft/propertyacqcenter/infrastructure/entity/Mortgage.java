package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.MortgageDto;
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
    private MortgageType mortgageType;
    private Double mortgageAmount;
    private Double downPayment;
    private Integer fixedRateTermYears;
    private Double fixedMortgageRatePercentage;
    private LocalDate firstPaymentDate;
    private String compoundFrequency;//TODO: por definir
    private Double balloonPayment;
    private String adjustableRateDetails;//TODO: por definir

    public Mortgage(MortgageDto dto) {
        this.id = dto.getId();
        this.mortgageType = dto.getMortgageType();
        this.mortgageAmount = dto.getMortgageAmount();
        this.downPayment = dto.getDownPayment();
        this.fixedRateTermYears = dto.getFixedRateTermYears();
        this.fixedMortgageRatePercentage = dto.getFixedMortgageRatePercentage();
        this.firstPaymentDate = dto.getFirstPaymentDate();
        this.compoundFrequency = dto.getCompoundFrequency();
        this.balloonPayment = dto.getBalloonPayment();
        this.adjustableRateDetails = dto.getAdjustableRateDetails();
        this.property = dto.getProperty() != null ? new Property(dto.getProperty()) : null;
    }

    public MortgageDto toAggregate() {
        return MortgageDto.builder()
                .id(this.id)
                .mortgageType(mortgageType)
                .mortgageAmount(mortgageAmount)
                .downPayment(downPayment)
                .fixedRateTermYears(fixedRateTermYears)
                .fixedMortgageRatePercentage(fixedMortgageRatePercentage)
                .firstPaymentDate(firstPaymentDate)
                .compoundFrequency(compoundFrequency)
                .balloonPayment(balloonPayment)
                .adjustableRateDetails(adjustableRateDetails)
                .property(property.toAggregateBasic())
                .build();
    }
}