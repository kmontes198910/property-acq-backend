package com.kynsoft.propertyacqcenter.application.query.restEstimate.getRentEstimateExternalService;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.rentcast.RentEstimateResponse;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.rentEstimate.RentCastRentEstimateServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class GetRentValueExternalServiceQueryHandler implements IQueryHandler<GetRentEstimateExternalServiceQuery, RentEstimateResponse>{

    private final RentCastRentEstimateServiceImpl resCastRentEstimateServiceImpl;

    public GetRentValueExternalServiceQueryHandler(RentCastRentEstimateServiceImpl resCastRentEstimateServiceImpl) {
        this.resCastRentEstimateServiceImpl = resCastRentEstimateServiceImpl;
    }

    @Override
    public RentEstimateResponse handle(GetRentEstimateExternalServiceQuery query) {

        return this.resCastRentEstimateServiceImpl.getRentEstimate(
                query.getAddress(), 
                query.getPropertyType(), 
                query.getLatitude(), 
                query.getLongitude(), 
                query.getBedrooms(), 
                query.getBathrooms(), 
                query.getSquareFootage(), 
                query.getDaysOld(),
                query.getCompCount()
        );
    }
}
