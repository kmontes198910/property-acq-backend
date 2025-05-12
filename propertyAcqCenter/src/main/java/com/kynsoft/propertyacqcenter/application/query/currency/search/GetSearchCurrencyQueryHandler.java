package com.kynsoft.propertyacqcenter.application.query.currency.search;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.domain.services.ICurrencyService;
import org.springframework.stereotype.Component;

@Component
public class GetSearchCurrencyQueryHandler implements IQueryHandler<GetSearchCurrencyQuery, PaginatedResponse> {

    private final ICurrencyService currencyService;

    public GetSearchCurrencyQueryHandler(ICurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @Override
    public PaginatedResponse handle(GetSearchCurrencyQuery query) {
        return this.currencyService.search(query.getPageable(), query.getFilter());
    }
}
