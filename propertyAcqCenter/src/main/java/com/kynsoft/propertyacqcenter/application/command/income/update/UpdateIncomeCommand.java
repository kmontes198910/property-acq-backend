package com.kynsoft.propertyacqcenter.application.command.income.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
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
    private Boolean increaseTypePercentage;
    private Boolean increaseFixedDollarAmount;

    public UpdateIncomeCommand(UUID id, String property, Double grossMonthlyIncome, Double totalNetMonthlyIncome, Double increaseRate, Boolean increaseTypePercentage, Boolean increaseFixedDollarAmount) {
        this.id = id;
        this.property = property;
        this.grossMonthlyIncome = grossMonthlyIncome;
        this.totalNetMonthlyIncome = totalNetMonthlyIncome;
        this.increaseRate = increaseRate;
        this.increaseTypePercentage = increaseTypePercentage;
        this.increaseFixedDollarAmount = increaseFixedDollarAmount;
    }

    public static UpdateIncomeCommand fromRequest(UpdateIncomeRequest request, UUID id) {
        return new UpdateIncomeCommand(
                id,
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
        return new UpdateIncomeMessage(id);
    }
}
