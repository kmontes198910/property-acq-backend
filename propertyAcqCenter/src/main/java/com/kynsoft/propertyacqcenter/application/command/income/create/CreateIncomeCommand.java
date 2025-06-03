package com.kynsoft.propertyacqcenter.application.command.income.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateIncomeCommand implements ICommand {

    private UUID id;
    private String property;
    private Double grossMonthlyIncome;
    private Double totalNetMonthlyIncome;
    private Double increaseRate;
    private Boolean increaseTypePercentage;
    private Boolean increaseFixedDollarAmount;

    public CreateIncomeCommand(String property, Double grossMonthlyIncome, Double totalNetMonthlyIncome, Double increaseRate, Boolean increaseTypePercentage, Boolean increaseFixedDollarAmount) {
        this.id = UUID.randomUUID();
        this.property = property;
        this.grossMonthlyIncome = grossMonthlyIncome;
        this.totalNetMonthlyIncome = totalNetMonthlyIncome;
        this.increaseRate = increaseRate;
        this.increaseTypePercentage = increaseTypePercentage;
        this.increaseFixedDollarAmount = increaseFixedDollarAmount;
    }

    public static CreateIncomeCommand fromRequest(CreateIncomeRequest request) {
        return new CreateIncomeCommand(
                request.getProperty(),
                request.getGrossMonthlyIncome(),
                request.getTotalNetMonthlyIncome(),
                request.getIncreaseRate(),
                request.getIncreaseTypePercentage(),
                request.getIncreaseFixedDollarAmount()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateIncomeMessage(id);
    }
}
