package com.kynsoft.propertyacqcenter.infrastructure.entity;

import com.kynsoft.propertyacqcenter.domain.dto.ExpensesDto;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "expenses")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Expenses implements Serializable {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "property_id")
    private Property property;

    private Double totalAmountExpenses;
    private Double increaseRate;
    private Boolean percentage;
    private Boolean fixedDollarAmount;

    private Double accounting;
    private Double electricity;
    private Double gas;
    private Double mortgageInsurance;
    private Double poolSpaService;
    private Double sewerWater;
    private Double trash;
    private Double advertising;
    private Double fireInsurance;
    private Double janitorialService;
    private Double liabilityInsurance;
    private Double otherUtilities;
    private Double propertyTaxes;
    private Double supplies;
    private Double workmans;
    private Double associationFees;
    private Double floodInsurance;
    private Double landscaping;
    private Double licenses;
    private Double payroll;
    private Double repairMaintenance;
    private Double telephone;
    private Double miscellaneous;
    private Double legal;

    public Expenses(ExpensesDto dto) {
        this.id = dto.getId();
        this.property = dto.getProperty() != null ? new Property(dto.getProperty()) : null;
        this.totalAmountExpenses = dto.getTotalAmountExpenses();
        this.increaseRate = dto.getIncreaseRate();
        this.percentage = dto.getPercentage();
        this.fixedDollarAmount = dto.getFixedDollarAmount();

        this.accounting = dto.getAccounting();
        this.electricity = dto.getElectricity();
        this.gas = dto.getGas();
        this.mortgageInsurance = dto.getMortgageInsurance();
        this.poolSpaService = dto.getPoolSpaService();
        this.sewerWater = dto.getSewerWater();
        this.trash = dto.getTrash();
        this.advertising = dto.getAdvertising();
        this.fireInsurance = dto.getFireInsurance();
        this.janitorialService = dto.getJanitorialService();
        this.liabilityInsurance = dto.getLiabilityInsurance();
        this.otherUtilities = dto.getOtherUtilities();
        this.propertyTaxes = dto.getPropertyTaxes();
        this.supplies = dto.getSupplies();
        this.workmans = dto.getWorkmans();
        this.associationFees = dto.getAssociationFees();
        this.floodInsurance = dto.getFloodInsurance();
        this.landscaping = dto.getLandscaping();
        this.licenses = dto.getLicenses();
        this.payroll = dto.getPayroll();
        this.repairMaintenance = dto.getRepairMaintenance();
        this.telephone = dto.getTelephone();
        this.miscellaneous = dto.getMiscellaneous();
        this.legal = dto.getLegal();
    }

    public ExpensesDto toAggregate() {
        return ExpensesDto.builder()
                .id(this.id)
                .property(property.toAggregateBasic())
                .totalAmountExpenses(totalAmountExpenses)
                .increaseRate(increaseRate)
                .percentage(percentage)
                .fixedDollarAmount(fixedDollarAmount)

                .accounting(accounting)
                .electricity(electricity)
                .gas(gas)
                .mortgageInsurance(mortgageInsurance)
                .poolSpaService(poolSpaService)
                .sewerWater(sewerWater)
                .trash(trash)
                .advertising(advertising)
                .fireInsurance(fireInsurance)
                .janitorialService(janitorialService)
                .liabilityInsurance(liabilityInsurance)
                .otherUtilities(otherUtilities)
                .propertyTaxes(propertyTaxes)
                .supplies(supplies)
                .workmans(workmans)
                .associationFees(associationFees)
                .floodInsurance(floodInsurance)
                .landscaping(landscaping)
                .licenses(licenses)
                .payroll(payroll)
                .repairMaintenance(repairMaintenance)
                .telephone(telephone)
                .miscellaneous(miscellaneous)
                .legal(legal)
                .build();
    }
}
