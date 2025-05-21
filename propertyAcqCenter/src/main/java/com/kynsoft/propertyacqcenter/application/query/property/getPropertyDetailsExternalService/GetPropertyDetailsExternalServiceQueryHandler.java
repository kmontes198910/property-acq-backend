package com.kynsoft.propertyacqcenter.application.query.property.getPropertyDetailsExternalService;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.rentcast.PropertyResponse;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.property.RentCastPropertyServiceImpl;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GetPropertyDetailsExternalServiceQueryHandler implements IQueryHandler<GetPropertyDetailsExternalServiceQuery, PropertyResponse>{

    private final RentCastPropertyServiceImpl resCastPropertyServiceImpl;

    public GetPropertyDetailsExternalServiceQueryHandler(RentCastPropertyServiceImpl resCastPropertyServiceImpl) {
        this.resCastPropertyServiceImpl = resCastPropertyServiceImpl;
    }

    @Override
    public PropertyResponse handle(GetPropertyDetailsExternalServiceQuery query) {

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
        return response.get(0);
    }
}
