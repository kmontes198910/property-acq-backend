package com.kynsoft.propertyacqcenter.application.query.estimateValue.getRentEstimateExternalService;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.estimateValue.EstimatedValueResponse;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.rentEstimate.RentCastRentEstimateServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class GetRentValueExternalServiceQueryHandler implements IQueryHandler<GetRentEstimateExternalServiceQuery, EstimatedValueResponse>{

    private final RentCastRentEstimateServiceImpl resCastRentEstimateServiceImpl;

    public GetRentValueExternalServiceQueryHandler(RentCastRentEstimateServiceImpl resCastRentEstimateServiceImpl) {
        this.resCastRentEstimateServiceImpl = resCastRentEstimateServiceImpl;
    }

    @Override
    public EstimatedValueResponse handle(GetRentEstimateExternalServiceQuery query) {

        return new EstimatedValueResponse(this.resCastRentEstimateServiceImpl.getRentEstimate(query.getAddress()));
    }
}
