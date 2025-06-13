package com.kynsoft.propertyacqcenter.application.command.allAnalysis.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateAllAnalysisCommand implements ICommand {

    private UUID id;
    private String property;
    private CreateAllAnalysisPurchaseRequest purchase;
    private CreateAllAnalysisMortgageRequest mortgage;
    private CreateAllAnalysisIncomeRequest income;
    private CreateAllAnalysisExpensesRequest expenses;
    private CreateAllAnalysisSalesRequest sales;

    public CreateAllAnalysisCommand(String property, CreateAllAnalysisPurchaseRequest purchase, 
                                    CreateAllAnalysisMortgageRequest mortgage, CreateAllAnalysisIncomeRequest income, 
                                    CreateAllAnalysisExpensesRequest expenses, CreateAllAnalysisSalesRequest sales) {
        this.id = UUID.randomUUID();
        this.property = property;
        this.purchase = purchase;
        this.mortgage = mortgage;
        this.income = income;
        this.expenses = expenses;
        this.sales = sales;
    }

    public static CreateAllAnalysisCommand fromRequest(CreateAllAnalysisRequest request) {
        return new CreateAllAnalysisCommand(
                request.getProperty(),
                request.getPurchase(),
                request.getMortgage(),
                request.getIncome(),
                request.getExpenses(),
                request.getSales()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateAllAnalysisMessage(id);
    }
}
