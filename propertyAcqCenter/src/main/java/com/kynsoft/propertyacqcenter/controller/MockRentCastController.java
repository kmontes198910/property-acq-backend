package com.kynsoft.propertyacqcenter.controller;

import com.kynsoft.propertyacqcenter.application.response.rentcast.PropertyResponse;
import com.kynsoft.propertyacqcenter.domain.dto.property.saleListing.SaleListingDto;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.estimate.dto.EstimatedValueDto;
import com.kynsoft.propertyacqcenter.infrastructure.services.mock.RentCastServiceMockImpl;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/rentcast/mock")
public class MockRentCastController {

    private final RentCastServiceMockImpl mock;

    public MockRentCastController(RentCastServiceMockImpl mock) {
        this.mock = mock;
    }

    @GetMapping("/property/fake")
    public List<PropertyResponse> getFakeProperty() {
        
        return this.mock.getPropertyDetails();
    }

    @GetMapping("/value/fake")
    public EstimatedValueDto getFakeEstimatedValue() {
        return this.mock.getEstimatedValueDetail();
    }

    @GetMapping("/rent/fake")
    public EstimatedValueDto getFakeRentEstimate() {
        return this.mock.getRentEstimateDetail();
    }

    @GetMapping("/sale/fake")
    public List<SaleListingDto> getFakeSaleListings() {
        return this.mock.getSaleListings();
    }
}