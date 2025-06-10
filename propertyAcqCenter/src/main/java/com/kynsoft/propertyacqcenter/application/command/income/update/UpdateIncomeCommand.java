package com.kynsoft.propertyacqcenter.application.command.income.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.domain.enums.IncreaseType;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateIncomeCommand implements ICommand {

    private UUID id;
    private String property;
    private Double grossMonthlyIncome;
    private Double totalNetMonthlyIncome;
    private Double increaseRate;

    private IncreaseType increaseType;

    //Detail Breakdown
    private Double unitType;
    private Double quantity;
    private Double rentMo;
    private Double sqft;//Sqft
    private Double sqftValue;//$/SqFt
    private Double occupancy;
    private Double annualIncrease;
    //private Double avgMonthlyRent;//valor numérico auto calculable, se completa a partir del campo Rent/Mo.

    //Micellaneous income
    private Double depositForfeitures;
    private Double sectino8Income;
    private Double incomefromInterest;
    private Double vendingMachines;
    private Double lateCharges;
    private Double laundryRoom;
    private Double other;

    //Management Fees
    private Double propertyManagementRate;
    private Double leasingCommissionRate;
    private Double leasingCommision;

    private Double porcentageIncreaseType;
    private Double fixedDollarAmount;

    private List<UpdateIncomeDetailsBreakdownRequest> detailsBreakdown;

    public UpdateIncomeCommand(UUID id, String property, Double grossMonthlyIncome, 
                               Double totalNetMonthlyIncome, Double increaseRate, Double unitType, Double quantity, Double rentMo, 
                               Double sqft, Double sqftValue, Double occupancy, Double annualIncrease, 
                               Double depositForfeitures, Double sectino8Income, Double incomefromInterest, 
                               Double vendingMachines, Double lateCharges, Double laundryRoom, Double other, 
                               Double propertyManagementRate, Double leasingCommissionRate, Double leasingCommision, 
                               Double porcentageIncreaseType, Double fixedDollarAmount, IncreaseType increaseType,
                               List<UpdateIncomeDetailsBreakdownRequest> detailsBreakdown) {
        this.id = id;
        this.property = property;
        this.grossMonthlyIncome = grossMonthlyIncome;
        this.totalNetMonthlyIncome = totalNetMonthlyIncome;
        this.increaseRate = increaseRate;
        this.unitType = unitType;
        this.quantity = quantity;
        this.rentMo = rentMo;
        this.sqft = sqft;
        this.sqftValue = sqftValue;
        this.occupancy = occupancy;
        this.annualIncrease = annualIncrease;
        this.depositForfeitures = depositForfeitures;
        this.sectino8Income = sectino8Income;
        this.incomefromInterest = incomefromInterest;
        this.vendingMachines = vendingMachines;
        this.lateCharges = lateCharges;
        this.laundryRoom = laundryRoom;
        this.other = other;
        this.propertyManagementRate = propertyManagementRate;
        this.leasingCommissionRate = leasingCommissionRate;
        this.leasingCommision = leasingCommision;
        this.porcentageIncreaseType = porcentageIncreaseType;
        this.fixedDollarAmount = fixedDollarAmount;
        this.increaseType = increaseType;
        this.detailsBreakdown = detailsBreakdown;
    }

    public static UpdateIncomeCommand fromRequest(UpdateIncomeRequest request, UUID id) {
        return new UpdateIncomeCommand(
                id,
                request.getProperty(),
                request.getGrossMonthlyIncome(),
                request.getTotalNetMonthlyIncome(),
                request.getIncreaseRate(),
                request.getUnitType(),
                request.getQuantity(),
                request.getRentMo(),
                request.getSqft(),
                request.getSqftValue(),
                request.getOccupancy(),
                request.getAnnualIncrease(),
                request.getDepositForfeitures(),
                request.getSectino8Income(),
                request.getIncomefromInterest(),
                request.getVendingMachines(),
                request.getLateCharges(),
                request.getLaundryRoom(),
                request.getOther(),
                request.getPropertyManagementRate(),
                request.getLeasingCommissionRate(),
                request.getLeasingCommision(),
                request.getPorcentageIncreaseType(),
                request.getFixedDollarAmount(),
                request.getIncreaseType(),
                request.getDetailsBreakdown()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateIncomeMessage(id);
    }
}
