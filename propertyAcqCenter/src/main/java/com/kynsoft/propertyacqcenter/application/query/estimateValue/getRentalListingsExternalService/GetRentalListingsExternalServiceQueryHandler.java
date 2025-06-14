package com.kynsoft.propertyacqcenter.application.query.estimateValue.getRentalListingsExternalService;

import com.kynsof.share.core.domain.bus.query.IQueryHandler;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.rental.RentCastRentalServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class GetRentalListingsExternalServiceQueryHandler implements IQueryHandler<GetRentalListingsExternalServiceQuery, PaginatedResponse>{

    private final RentCastRentalServiceImpl rentalServiceImpl;

    public GetRentalListingsExternalServiceQueryHandler(RentCastRentalServiceImpl rentalServiceImpl) {
        this.rentalServiceImpl = rentalServiceImpl;
    }

    @Override
    public PaginatedResponse handle(GetRentalListingsExternalServiceQuery query) {

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
        return new PaginatedResponse(this.rentalServiceImpl.getRentEstimate(
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
