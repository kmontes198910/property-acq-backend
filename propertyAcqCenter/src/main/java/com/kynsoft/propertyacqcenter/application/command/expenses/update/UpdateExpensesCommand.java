package com.kynsoft.propertyacqcenter.application.command.expenses.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateExpensesCommand implements ICommand {

    private UUID id;
    private String property;
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

    public UpdateExpensesCommand(UUID id, String property, Double totalAmountExpenses, Double increaseRate, 
                                 Boolean percentage, Boolean fixedDollarAmount, 
                                 Double accounting, Double electricity, Double gas, 
                                 Double mortgageInsurance, Double poolSpaService, 
                                 Double sewerWater, Double trash, Double advertising, 
                                 Double fireInsurance, Double janitorialService, 
                                 Double liabilityInsurance, Double otherUtilities, 
                                 Double propertyTaxes, Double supplies, Double workmans, 
                                 Double associationFees, Double floodInsurance, Double landscaping, 
                                 Double licenses, Double payroll, Double repairMaintenance, 
                                 Double telephone, Double miscellaneous, Double legal) {
        this.id = id;
        this.property = property;
        this.totalAmountExpenses = totalAmountExpenses;
        this.increaseRate = increaseRate;
        this.percentage = percentage;
        this.fixedDollarAmount = fixedDollarAmount;
        this.accounting = accounting;
        this.electricity = electricity;
        this.gas = gas;
        this.mortgageInsurance = mortgageInsurance;
        this.poolSpaService = poolSpaService;
        this.sewerWater = sewerWater;
        this.trash = trash;
        this.advertising = advertising;
        this.fireInsurance = fireInsurance;
        this.janitorialService = janitorialService;
        this.liabilityInsurance = liabilityInsurance;
        this.otherUtilities = otherUtilities;
        this.propertyTaxes = propertyTaxes;
        this.supplies = supplies;
        this.workmans = workmans;
        this.associationFees = associationFees;
        this.floodInsurance = floodInsurance;
        this.landscaping = landscaping;
        this.licenses = licenses;
        this.payroll = payroll;
        this.repairMaintenance = repairMaintenance;
        this.telephone = telephone;
        this.miscellaneous = miscellaneous;
        this.legal = legal;
    }

    public static UpdateExpensesCommand fromRequest(UpdateExpensesRequest request, UUID id) {
        return new UpdateExpensesCommand(
                id,
                request.getProperty(),
                request.getTotalAmountExpenses(),
                request.getIncreaseRate(),
                request.getPercentage(),
                request.getFixedDollarAmount(),
                request.getAccounting(),
                request.getElectricity(),
                request.getGas(),
                request.getMortgageInsurance(),
                request.getPoolSpaService(),
                request.getSewerWater(),
                request.getTrash(),
                request.getAdvertising(),
                request.getFireInsurance(),
                request.getJanitorialService(),
                request.getLiabilityInsurance(),
                request.getOtherUtilities(),
                request.getPropertyTaxes(),
                request.getSupplies(),
                request.getWorkmans(),
                request.getAssociationFees(),
                request.getFloodInsurance(),
                request.getLandscaping(),
                request.getLicenses(),
                request.getPayroll(),
                request.getRepairMaintenance(),
                request.getTelephone(),
                request.getMiscellaneous(),
                request.getLegal()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateExpensesMessage(id);
    }
}
