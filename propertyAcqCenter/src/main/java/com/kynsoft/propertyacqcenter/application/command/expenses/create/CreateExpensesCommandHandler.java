package com.kynsoft.propertyacqcenter.application.command.expenses.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.ExpensesDto;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.services.IExpensesService;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import org.springframework.stereotype.Component;

@Component
public class CreateExpensesCommandHandler implements ICommandHandler<CreateExpensesCommand> {

    private final IExpensesService expensesService;
    private final IPropertyService propertyService;

    public CreateExpensesCommandHandler(IExpensesService expensesService, IPropertyService propertyService) {
        this.expensesService = expensesService;
        this.propertyService = propertyService;
    }

    @Override
    public void handle(CreateExpensesCommand command) {
        PropertyDto property = this.propertyService.getById(command.getProperty());

        this.expensesService.create(ExpensesDto.builder()
                .id(command.getId())
                .property(property)
                .totalAmountExpenses(command.getTotalAmountExpenses())
                .increaseRate(command.getIncreaseRate())
                .percentage(command.getPercentage())
                .fixedDollarAmount(command.getFixedDollarAmount())
                .accounting(command.getAccounting())
                .electricity(command.getElectricity())
                .gas(command.getGas())
                .mortgageInsurance(command.getMortgageInsurance())
                .poolSpaService(command.getPoolSpaService())
                .sewerWater(command.getSewerWater())
                .trash(command.getTrash())
                .advertising(command.getAdvertising())
                .fireInsurance(command.getFireInsurance())
                .janitorialService(command.getJanitorialService())
                .liabilityInsurance(command.getLiabilityInsurance())
                .otherUtilities(command.getOtherUtilities())
                .propertyTaxes(command.getPropertyTaxes())
                .supplies(command.getSupplies())
                .workmans(command.getWorkmans())
                .associationFees(command.getAssociationFees())
                .floodInsurance(command.getFloodInsurance())
                .landscaping(command.getLandscaping())
                .licenses(command.getLicenses())
                .payroll(command.getPayroll())
                .repairMaintenance(command.getRepairMaintenance())
                .telephone(command.getTelephone())
                .miscellaneous(command.getMiscellaneous())
                .legal(command.getLegal())
                .increaseType(command.getIncreaseType())
                .build()
        );
    }

}
