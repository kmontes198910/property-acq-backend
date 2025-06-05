package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.*;
import com.kynsoft.propertyacqcenter.domain.enums.IncreaseType;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class ExpensesResponse implements Serializable, IResponse {

    private UUID id;
    private PropertiesBasicResponse property;
    private IncreaseType increaseType;

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

    public ExpensesResponse(ExpensesDto dto) {
        this.id = dto.getId();
        this.property = dto.getProperty() != null ? new PropertiesBasicResponse(dto.getProperty()) : null;
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
        this.increaseType = dto.getIncreaseType();
    }

}
