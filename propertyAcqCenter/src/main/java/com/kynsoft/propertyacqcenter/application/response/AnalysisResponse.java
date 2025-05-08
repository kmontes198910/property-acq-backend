package com.kynsoft.propertyacqcenter.application.response;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.*;
import com.kynsoft.propertyacqcenter.domain.dto.analysis.CompsAtAGlanceDto;
import com.kynsoft.propertyacqcenter.domain.dto.analysis.LastSaleDto;
import com.kynsoft.propertyacqcenter.domain.dto.analysis.MortageDebtDto;
import com.kynsoft.propertyacqcenter.domain.dto.analysis.OpportunityDto;
import com.kynsoft.propertyacqcenter.domain.dto.analysis.PropertyComparableDto;
import com.kynsoft.propertyacqcenter.domain.dto.analysis.SaleValueDto;
import com.kynsoft.propertyacqcenter.domain.dto.analysis.TaxAssessmentAnalysisDto;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AnalysisResponse implements IResponse {
    private UUID id;
    private PropertyDto property;
    private OpportunityDto opportunity;
    private MortageDebtDto mortageDebt;
    private CompsAtAGlanceDto compsAtAGlance;
    private LastSaleDto lastSale;
    private List<SaleValueDto> saleValue;
    private List<TaxAssessmentAnalysisDto> taxAssessments;
    private List<PropertyComparableDto> comparables;

    public AnalysisResponse(AnalysisDto dto) {
        this.id = dto.getId();
        this.property = dto.getProperty();
        this.opportunity = dto.getOpportunity();
        this.mortageDebt = dto.getMortageDebt();
        this.compsAtAGlance = dto.getCompsAtAGlance();
        this.lastSale = dto.getLastSale();
        this.saleValue = dto.getSaleValue();
        this.taxAssessments = dto.getTaxAssessments();
        this.comparables = dto.getComparables();
    }

}
