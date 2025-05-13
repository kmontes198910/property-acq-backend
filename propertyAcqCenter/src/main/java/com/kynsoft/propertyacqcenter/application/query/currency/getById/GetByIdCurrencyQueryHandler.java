package com.kynsoft.propertyacqcenter.application.query.currency.getById;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.CurrencyResponse;
import com.kynsoft.propertyacqcenter.domain.services.ICurrencyService;
import org.springframework.stereotype.Component;

@Component
public class GetByIdCurrencyQueryHandler implements IQueryHandler<GetByIdCurrencyQuery, CurrencyResponse> {

    private final ICurrencyService currencyService;

    public GetByIdCurrencyQueryHandler(ICurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Override
    public CurrencyResponse handle(GetByIdCurrencyQuery query) {
        System.err.println("###################################################");
        System.err.println("###################################################");
        System.err.println("###################################################");
        System.err.println("###################################################");
        return new CurrencyResponse(this.currencyService.findById(query.getId()));
    }
}
