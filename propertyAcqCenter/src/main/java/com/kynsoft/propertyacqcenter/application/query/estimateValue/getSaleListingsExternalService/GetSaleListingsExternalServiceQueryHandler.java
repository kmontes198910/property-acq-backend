package com.kynsoft.propertyacqcenter.application.query.estimateValue.getSaleListingsExternalService;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.sale.RentCastSaleServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class GetSaleListingsExternalServiceQueryHandler implements IQueryHandler<GetSaleListingsExternalServiceQuery, PaginatedResponse>{

    private final RentCastSaleServiceImpl resCastSaleServiceImpl;

    public GetSaleListingsExternalServiceQueryHandler(RentCastSaleServiceImpl resCastSaleServiceImpl) {
        this.resCastSaleServiceImpl = resCastSaleServiceImpl;
    }

    @Override
    public PaginatedResponse handle(GetSaleListingsExternalServiceQuery query) {

//        return this.resCastSaleServiceImpl.getRentEstimate(
//                query.getAddress(), 
//                query.getPropertyType(), 
//                query.getCity(), 
//                query.getState(), 
//                query.getZipCode(), 
//                query.getLatitude(), 
//                query.getLongitude(), 
//                query.getRadius(), 
//                query.getBedrooms(), 
//                query.getBathrooms(), 
//                query.getStatus(), 
//                query.getDaysOld());
        return new PaginatedResponse(this.resCastSaleServiceImpl.getRentEstimate(
                query.getAddress(), 
                query.getPropertyType(), 
                query.getCity(), 
                query.getState(), 
                query.getZipCode(), 
                query.getLatitude(), 
                query.getLongitude(), 
                query.getRadius(), 
                query.getBedrooms(), 
                query.getBathrooms(), 
                query.getStatus(), 
                query.getDaysOld()), 
                0, 
                0,
                0L, 
                0, 
                0
        );
    }
}
