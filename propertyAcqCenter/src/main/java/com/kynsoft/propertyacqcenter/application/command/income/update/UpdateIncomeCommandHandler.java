package com.kynsoft.propertyacqcenter.application.command.income.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.IncomeDetailsBreakdownDto;
import com.kynsoft.propertyacqcenter.domain.dto.IncomeDto;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.services.IIncomeService;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
                .increaseType(command.getIncreaseType())
                .detailsBreakdown(this.detailsBreakdownValues(command))
                .build()
        );
    }

    private List<IncomeDetailsBreakdownDto> detailsBreakdownValues(UpdateIncomeCommand command){
        List<IncomeDetailsBreakdownDto> values = new ArrayList<>();
        if (command.getDetailsBreakdown() != null) {
            command.getDetailsBreakdown().forEach(x -> {
                values.add(IncomeDetailsBreakdownDto.builder()
                        .id(x.getId() != null ? x.getId() : UUID.randomUUID())
                        .unitType(x.getUnitType())
                        .quantity(x.getQuantity())
                        .rentMo(x.getRentMo())
                        .sqft(x.getSqft())
                        .sqftValue(x.getSqftValue())
                        .occupancy(x.getOccupancy())
                        .annualIncrease(x.getAnnualIncrease())
                        .build()
                );
            });
        }
        return values;
    }

}
