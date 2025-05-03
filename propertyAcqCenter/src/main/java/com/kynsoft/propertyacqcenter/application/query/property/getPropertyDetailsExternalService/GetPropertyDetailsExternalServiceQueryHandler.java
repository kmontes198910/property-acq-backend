package com.kynsoft.propertyacqcenter.application.query.property.getPropertyDetailsExternalService;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsoft.propertyacqcenter.application.response.property.PropertyDasboardResponse;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.property.RentCastPropertyServiceImpl;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.property.dto.PropertyDto;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class GetPropertyDetailsExternalServiceQueryHandler implements IQueryHandler<GetPropertyDetailsExternalServiceQuery, PropertyDasboardResponse>{

    private final RentCastPropertyServiceImpl resCastPropertyServiceImpl;

    public GetPropertyDetailsExternalServiceQueryHandler(RentCastPropertyServiceImpl resCastPropertyServiceImpl) {
        this.resCastPropertyServiceImpl = resCastPropertyServiceImpl;
    }

    @Override
    public PropertyDasboardResponse handle(GetPropertyDetailsExternalServiceQuery query) {

        List<PropertyDto> response = this.resCastPropertyServiceImpl.getPropertyDetails(query.getAddress());
        return new PropertyDasboardResponse(
                response.get(0).getId(), 
                response.get(0).getFormattedAddress(), 
                response.get(0).getAddressLine1(), 
                response.get(0).getAddressLine2(), 
                response.get(0).getCity(), 
                response.get(0).getState(), 
                response.get(0).getZipCode(), 
                response.get(0).getCounty(), 
                response.get(0).getPropertyType(), 
                response.get(0).getBedrooms(), 
                response.get(0).getBathrooms(), 
                response.get(0).getSquareFootage(), 
                response.get(0).getLotSize(), 
                response.get(0).getYearBuilt(), 
                response.get(0).getAssessorID(), 
                response.get(0).getLastSaleDate(), 
                response.get(0).getLastSalePrice(), 
                response.get(0).isOwnerOccupied(), 
                List.of()
        );
    }
}
