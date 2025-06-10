package com.kynsoft.propertyacqcenter.application.command.allAnalysis.create;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAllAnalysisRequest {

    private String property;
    private CreateAllAnalysisPurchaseRequest purchase;
    private CreateAllAnalysisMortgageRequest mortgage;
    private CreateAllAnalysisIncomeRequest income;
    private CreateAllAnalysisExpensesRequest expenses;
}
