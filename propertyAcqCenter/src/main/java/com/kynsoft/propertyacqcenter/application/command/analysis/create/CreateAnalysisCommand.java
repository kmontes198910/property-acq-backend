package com.kynsoft.propertyacqcenter.application.command.analysis.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateAnalysisCommand implements ICommand {

    private UUID id;
    private UUID userId;
    private String property;
    private OpportunityRequest opportunity;
    private StatisticsRequest statistics;
    private MortageDebtRequest mortageDebt;
    private CompsAtAGlanceRequest compsAtAGlance;
    private LastSaleRequest lastSale;
    private List<SaleValueRequest> saleValue;
    private List<TaxAssessmentAnalysisRequest> taxAssessments;
    private List<PropertyComparableRequest> comparables;

    public CreateAnalysisCommand(UUID userId, String property, StatisticsRequest statistics, OpportunityRequest opportunity, MortageDebtRequest mortageDebt, CompsAtAGlanceRequest compsAtAGlance, LastSaleRequest lastSale, List<SaleValueRequest> saleValue, List<TaxAssessmentAnalysisRequest> taxAssessments, List<PropertyComparableRequest> comparables) {
        this.id = UUID.randomUUID();
        this.userId = userId;
        this.property = property;
        this.statistics = statistics;
        this.opportunity = opportunity;
        this.mortageDebt = mortageDebt;
        this.compsAtAGlance = compsAtAGlance;
        this.lastSale = lastSale;
        this.saleValue = saleValue;
        this.taxAssessments = taxAssessments;
        this.comparables = comparables;
    }

    public static CreateAnalysisCommand fromRequest(AnalysisRequest request, UUID userId) {
        return new CreateAnalysisCommand(
                userId,
                request.getProperty(),
                request.getStatistics(),
                request.getOpportunity(),
                request.getMortageDebt(),
                request.getCompsAtAGlance(),
                request.getLastSale(),
                request.getSaleValue(),
                request.getTaxAssessments(),
                request.getComparables()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateAnalysisMessage(id);
    }
}
