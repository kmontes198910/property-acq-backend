package com.kynsof.identity.infrastructure.services.test;


import java.util.List;

public interface IRentCastService {
    List<PropertyResponse> getPropertyDetails(String address);
    EstimatedValueResponse getEstimatedValueDetail(String address);
    RentEstimateResponse getRentEstimateDetail(String address);
    List<SaleListingDto> getSaleListings(String city, String state);
}