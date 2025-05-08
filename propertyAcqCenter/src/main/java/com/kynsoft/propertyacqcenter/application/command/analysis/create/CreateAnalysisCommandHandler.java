package com.kynsoft.propertyacqcenter.application.command.analysis.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.AnalysisDto;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.dto.analysis.CompsAtAGlanceDto;
import com.kynsoft.propertyacqcenter.domain.dto.analysis.LastSaleDto;
import com.kynsoft.propertyacqcenter.domain.dto.analysis.MortageDebtDto;
import com.kynsoft.propertyacqcenter.domain.dto.analysis.OpportunityDto;
import com.kynsoft.propertyacqcenter.domain.dto.analysis.PropertyComparableDto;
import com.kynsoft.propertyacqcenter.domain.dto.analysis.SaleValueDto;
import com.kynsoft.propertyacqcenter.domain.dto.analysis.TaxAssessmentAnalysisDto;
import com.kynsoft.propertyacqcenter.domain.services.IAnalysisService;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Component;

@Component
public class CreateAnalysisCommandHandler implements ICommandHandler<CreateAnalysisCommand> {

    private final IAnalysisService analysisService;
    private final IPropertyService propertyService;

    public CreateAnalysisCommandHandler(IAnalysisService analysisService, IPropertyService propertyService) {
        this.analysisService = analysisService;
        this.propertyService = propertyService;
    }

    @Override
    public void handle(CreateAnalysisCommand command) {
        PropertyDto propertyDto = this.propertyService.getById(command.getProperty());
        this.analysisService.create(AnalysisDto.builder()
                .id(UUID.randomUUID())
                .property(propertyDto)
                .opportunity(OpportunityDto.builder()
                        .estWholesalePrice(command.getOpportunity().getEstWholesalePrice())
                        .grossYield(command.getOpportunity().getGrossYield())
                        .id(UUID.randomUUID())
                        .linkedProperties(command.getOpportunity().getLinkedProperties())
                        .monthlyRent(command.getOpportunity().getMonthlyRent())
                        .build())
                .mortageDebt(MortageDebtDto.builder()
                        .estimatedBalance(command.getMortageDebt().getEstimatedBalance())
                        .id(UUID.randomUUID())
                        .involuntaryAmount(command.getMortageDebt().getInvoluntaryAmount())
                        .involuntaryLiens(command.getMortageDebt().getInvoluntaryLiens())
                        .openMortages(command.getMortageDebt().getOpenMortages())
                        .build())
                .compsAtAGlance(CompsAtAGlanceDto.builder()
                        .id(UUID.randomUUID())
                        .avgSalePrice(command.getCompsAtAGlance().getAvgSalePrice())
                        .dayOnMarket(command.getCompsAtAGlance().getDayOnMarket())
                        .build())
                .lastSale(LastSaleDto.builder()
                        .documentType(command.getLastSale().getDocumentType())
                        .id(UUID.randomUUID())
                        .mls(command.getLastSale().getMls())
                        .publicRecord(command.getLastSale().getPublicRecord())
                        .build())
                .comparables( this.comparables(command))
                .saleValue(this.salesValues(command))
                .taxAssessments(this.taxAssessmentAnalysis(command))
                .build());
    }

    private List<PropertyComparableDto> comparables(CreateAnalysisCommand command){
        List<PropertyComparableDto> comparable = new ArrayList<>();
        if (command.getComparables() != null) {
            command.getComparables().forEach(x -> {
                comparable.add(PropertyComparableDto.builder()
                        .formattedAddress(x.getFormattedAddress())
                        .id(UUID.randomUUID())
                        .lastSeenDate(x.getLastSeenDate())
                        .latitude(x.getLatitude())
                        .longitude(x.getLongitude())
                        .lotSize(x.getLotSize())
                        .price(x.getPrice())
                        .propertyType(x.getPropertyType())
                        .squareFootage(x.getSquareFootage())
                        .yearBuilt(x.getYearBuilt())
                        .build()
                );
            });
        }
        return comparable;
    }

    private List<SaleValueDto> salesValues(CreateAnalysisCommand command){
        List<SaleValueDto> values = new ArrayList<>();
        if (command.getSaleValue() != null) {
            command.getSaleValue().forEach(x -> {
                values.add(SaleValueDto.builder()
                        .id(UUID.randomUUID())
                        .estimatedValue(x.getEstimatedValue())
                        .lastYear(x.getLastYear())
                        .build()
                );
            });
        }
        return values;
    }

    private List<TaxAssessmentAnalysisDto> taxAssessmentAnalysis(CreateAnalysisCommand command){
        List<TaxAssessmentAnalysisDto> taxAssessments = new ArrayList<>();
        if (command.getTaxAssessments() != null) {
            command.getTaxAssessments().forEach(x -> {
                taxAssessments.add(TaxAssessmentAnalysisDto.builder()
                        .id(UUID.randomUUID())
                        .improvements(x.getImprovements())
                        .land(x.getLand())
                        .value(x.getValue())
                        .year(x.getYear())
                        .build()
                );
            });
        }
        return taxAssessments;
    }
}
