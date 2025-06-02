package com.kynsoft.propertyacqcenter.domain.services;


import com.kynsoft.propertyacqcenter.application.response.rentcast.EstimatedValueResponse;
import com.kynsoft.propertyacqcenter.application.response.rentcast.PropertyResponse;
import com.kynsoft.propertyacqcenter.application.response.rentcast.RentEstimateResponse;
import com.kynsoft.propertyacqcenter.application.response.rentcast.SaleListingResponse;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.property.dto.PropertyDto;

import java.util.List;
import java.util.UUID;

public interface IRentCastService {
    List<PropertyDto> getPropertyDetails(String address);
    EstimatedValueResponse getEstimatedValueDetail(String address);
    RentEstimateResponse getRentEstimateDetail(String address);
    List<SaleListingResponse> getSaleListings(String city, String state);

    List<UUID> createRentCastProperty(List<PropertyResponse> propertyResponses);
}