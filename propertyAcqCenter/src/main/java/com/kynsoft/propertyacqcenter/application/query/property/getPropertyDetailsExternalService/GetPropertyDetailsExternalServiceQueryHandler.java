package com.kynsoft.propertyacqcenter.application.query.property.getPropertyDetailsExternalService;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.application.response.rentcast.PropertyResponse;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.property.RentCastPropertyServiceImpl;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GetPropertyDetailsExternalServiceQueryHandler implements IQueryHandler<GetPropertyDetailsExternalServiceQuery, PaginatedResponse>{

    private final RentCastPropertyServiceImpl resCastPropertyServiceImpl;

    public GetPropertyDetailsExternalServiceQueryHandler(RentCastPropertyServiceImpl resCastPropertyServiceImpl) {
        this.resCastPropertyServiceImpl = resCastPropertyServiceImpl;
    }

    @Override
    public PaginatedResponse handle(GetPropertyDetailsExternalServiceQuery query) {

        List<PropertyResponse> response = this.resCastPropertyServiceImpl.getPropertyDetails(
                query.getAddress(), 
                query.getCity(), 
                query.getState(), 
                query.getPropertyType(), 
                query.getZipCode(), 
                query.getLatitude(), 
                query.getLongitude(), 
                query.getBedrooms(), 
                query.getBathrooms()
        );
        return new PaginatedResponse(response, 
                0, 
                0,
                0L, 
                0, 
                0
        );
//        return response.get(0);
    }
}
