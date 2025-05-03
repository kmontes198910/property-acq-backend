package com.kynsoft.propertyacqcenter.application.query.estimateValue.getEstimateValueExternalService;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.estimateValue.EstimatedValueResponse;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.estimateValue.RentCastEstimateValueServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class GetEstimateValueExternalServiceQueryHandler implements IQueryHandler<GetEstimateValueExternalServiceQuery, EstimatedValueResponse>{

    private final RentCastEstimateValueServiceImpl resCastEstimateValueServiceImpl;

    public GetEstimateValueExternalServiceQueryHandler(RentCastEstimateValueServiceImpl resCastEstimateValueServiceImpl) {
        this.resCastEstimateValueServiceImpl = resCastEstimateValueServiceImpl;
    }

    @Override
    public EstimatedValueResponse handle(GetEstimateValueExternalServiceQuery query) {

        return new EstimatedValueResponse(this.resCastEstimateValueServiceImpl.getEstimatedValue(query.getAddress()));
    }
}
