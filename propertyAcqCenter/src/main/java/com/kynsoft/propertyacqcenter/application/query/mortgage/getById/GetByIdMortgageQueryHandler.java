package com.kynsoft.propertyacqcenter.application.query.mortgage.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.MortgageResponse;
import com.kynsoft.propertyacqcenter.domain.dto.MortgageDto;
import com.kynsoft.propertyacqcenter.domain.services.IMortgageService;
import com.kynsoft.propertyacqcenter.infrastructure.services.AmortizationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GetByIdMortgageQueryHandler implements IQueryHandler<GetByIdMortgageQuery, MortgageResponse>{

    private final IMortgageService mortgageService;
    private final AmortizationService amortizationService;

    @Override
    public MortgageResponse handle(GetByIdMortgageQuery query) {
        MortgageDto dto = this.mortgageService.findById(query.getId());
        MortgageResponse response = new MortgageResponse(dto);
        response.setAmortizations(this.amortizationService.generateAmortizationSchedule(dto));
        return response;
    }
}
