package com.kynsoft.propertyacqcenter.application.query.estimateValue.getSaleListingsExternalService;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.rentcast.SaleListingResponse;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.sale.RentCastSaleServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class GetSaleListingsExternalServiceQueryHandler implements IQueryHandler<GetSaleListingsExternalServiceQuery, SaleListingResponse>{

    private final RentCastSaleServiceImpl resCastSaleServiceImpl;

    public GetSaleListingsExternalServiceQueryHandler(RentCastSaleServiceImpl resCastSaleServiceImpl) {
        this.resCastSaleServiceImpl = resCastSaleServiceImpl;
    }

    @Override
    public SaleListingResponse handle(GetSaleListingsExternalServiceQuery query) {

        return this.resCastSaleServiceImpl.getRentEstimate(query.getAddress()).get(0);
    }
}
