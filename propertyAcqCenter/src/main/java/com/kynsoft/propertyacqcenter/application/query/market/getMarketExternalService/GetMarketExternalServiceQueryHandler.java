package com.kynsoft.propertyacqcenter.application.query.market.getMarketExternalService;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.market.RealEstateResponse;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.market.MarketServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class GetMarketExternalServiceQueryHandler implements IQueryHandler<GetMarketExternalServiceQuery, RealEstateResponse>{

    private final MarketServiceImpl marketServiceImpl;

    public GetMarketExternalServiceQueryHandler(MarketServiceImpl marketServiceImpl) {
        this.marketServiceImpl = marketServiceImpl;
    }

    @Override
    public RealEstateResponse handle(GetMarketExternalServiceQuery query) {

        return this.marketServiceImpl.getMarketDetails(query.getDataType(), query.getZipCode(), query.getHistoryRange());
    }
}
