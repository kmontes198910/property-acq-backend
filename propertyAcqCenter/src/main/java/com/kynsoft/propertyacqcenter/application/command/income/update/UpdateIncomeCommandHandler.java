package com.kynsoft.propertyacqcenter.application.command.income.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.IncomeDto;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.services.IIncomeService;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import org.springframework.stereotype.Component;

@Component
public class UpdateIncomeCommandHandler implements ICommandHandler<UpdateIncomeCommand> {

    private final IIncomeService incomeService;
    private final IPropertyService propertyService;

    public UpdateIncomeCommandHandler(IIncomeService incomeService, IPropertyService propertyService) {
        this.incomeService = incomeService;
        this.propertyService = propertyService;
    }

    @Override
    public void handle(UpdateIncomeCommand command) {
        PropertyDto property = this.propertyService.getById(command.getProperty());

        this.incomeService.update(IncomeDto.builder()
                .id(command.getId())
                .property(property)
                .grossMonthlyIncome(command.getGrossMonthlyIncome())
                .totalNetMonthlyIncome(command.getTotalNetMonthlyIncome())
                .increaseRate(command.getIncreaseRate())
                .increaseFixedDollarAmount(command.getIncreaseFixedDollarAmount())
                .increaseTypePercentage(command.getIncreaseTypePercentage())
                .unitType(command.getUnitType())
                .quantity(command.getQuantity())
                .rentMo(command.getRentMo())
                .sqft(command.getSqft())
                .sqftValue(command.getSqftValue())
                .occupancy(command.getOccupancy())
                .annualIncrease(command.getAnnualIncrease())
                .depositForfeitures(command.getDepositForfeitures())
                .sectino8Income(command.getSectino8Income())
                .incomefromInterest(command.getIncomefromInterest())
                .vendingMachines(command.getVendingMachines())
                .lateCharges(command.getLateCharges())
                .laundryRoom(command.getLaundryRoom())
                .other(command.getOther())
                .propertyManagementRate(command.getPropertyManagementRate())
                .leasingCommissionRate(command.getLeasingCommissionRate())
                .leasingCommision(command.getLeasingCommision())
                .porcentageIncreaseType(command.getPorcentageIncreaseType())
                .fixedDollarAmount(command.getFixedDollarAmount())
                .build()
        );
    }

}
