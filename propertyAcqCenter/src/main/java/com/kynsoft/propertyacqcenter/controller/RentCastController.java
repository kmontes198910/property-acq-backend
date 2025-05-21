package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.query.dashboardRequest.EstimateEnum;
import com.kynsoft.propertyacqcenter.application.query.dashboardRequest.GetDashboardInfoQuery;
import com.kynsoft.propertyacqcenter.application.query.estimateValue.getEstimateValueExternalService.GetEstimateValueExternalServiceQuery;
import com.kynsoft.propertyacqcenter.application.query.estimateValue.getSaleListingsExternalService.GetSaleListingsExternalServiceQuery;
import com.kynsoft.propertyacqcenter.application.query.restEstimate.getRentEstimateExternalService.GetRentEstimateExternalServiceQuery;
import com.kynsoft.propertyacqcenter.application.query.property.getPropertyDetailsExternalService.GetPropertyDetailsExternalServiceQuery;
import com.kynsoft.propertyacqcenter.application.response.dashboardInfo.DashboardInfoResponse;
import com.kynsoft.propertyacqcenter.application.response.rentcast.EstimatedValueResponse;
import com.kynsoft.propertyacqcenter.application.response.rentcast.PropertyResponse;
import com.kynsoft.propertyacqcenter.application.response.rentcast.RentEstimateResponse;
import com.kynsoft.propertyacqcenter.application.response.rentcast.SaleListingResponse;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/rentcast")
public class RentCastController {

    private final IMediator mediator;

    public RentCastController(IMediator mediator) {
        this.mediator = mediator;
    }

    @GetMapping("/property")
    public ResponseEntity<PropertyResponse> getPropertyDetails(@RequestParam(defaultValue = "", required = false) String address,
                                                               @RequestParam(defaultValue = "", required = false) String city,
                                                               @RequestParam(defaultValue = "", required = false) String state,
                                                               @RequestParam(defaultValue = "", required = false) String zipCode,
                                                               @RequestParam(defaultValue = "Single Family", required = false) String propertyType,
                                                               @RequestParam(defaultValue = "-1", required = false) Double latitude,
                                                               @RequestParam(defaultValue = "-1", required = false) Double longitude,
                                                               @RequestParam(defaultValue = "-1", required = false) Double bedrooms,
                                                               @RequestParam(defaultValue = "-1", required = false) Double bathrooms
                                                               ) {
        GetPropertyDetailsExternalServiceQuery query = new GetPropertyDetailsExternalServiceQuery(address, city, state, propertyType, zipCode, latitude, longitude, bedrooms, bathrooms);
        PropertyResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/dashboard-info")
    public ResponseEntity<DashboardInfoResponse> getDashboardInfo(
            @RequestParam String address,
            @RequestParam(defaultValue = "PROPERTY_ESTIMATED", required = false) EstimateEnum estimate)
    {
        GetDashboardInfoQuery query = new GetDashboardInfoQuery(address, estimate);
        DashboardInfoResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/value/details")
    public ResponseEntity<EstimatedValueResponse> valueDetails(@RequestParam(defaultValue = "", required = false) String address,
                                                            @RequestParam(defaultValue = "Single Family", required = false) String propertyType,
                                                            @RequestParam(defaultValue = "-1", required = false) double latitude,
                                                            @RequestParam(defaultValue = "-1", required = false) double longitude,
                                                            @RequestParam(defaultValue = "-1", required = false) double bedrooms,
                                                            @RequestParam(defaultValue = "-1", required = false) double bathrooms,
                                                            @RequestParam(defaultValue = "-1", required = false) double squareFootage,
                                                            @RequestParam(defaultValue = "0", required = false) int daysOld) {
        GetEstimateValueExternalServiceQuery query = new GetEstimateValueExternalServiceQuery(address, propertyType, latitude, longitude, bedrooms, bathrooms, squareFootage, daysOld);
        EstimatedValueResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/rent/details")
    public ResponseEntity<RentEstimateResponse> rentDetails(@RequestParam(defaultValue = "", required = false) String address,
                                                            @RequestParam(defaultValue = "Single Family", required = false) String propertyType,
                                                            @RequestParam(defaultValue = "-1", required = false) double latitude,
                                                            @RequestParam(defaultValue = "-1", required = false) double longitude,
                                                            @RequestParam(defaultValue = "-1", required = false) double bedrooms,
                                                            @RequestParam(defaultValue = "-1", required = false) double bathrooms,
                                                            @RequestParam(defaultValue = "-1", required = false) double squareFootage,
                                                            @RequestParam(defaultValue = "0", required = false) int daysOld) {
        GetRentEstimateExternalServiceQuery query = new GetRentEstimateExternalServiceQuery(address, propertyType, latitude, longitude, bedrooms, bathrooms, squareFootage, daysOld);
        RentEstimateResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/sale")
    public SaleListingResponse getSaleListings(@RequestParam(defaultValue = "", required = false) String address,
                                               @RequestParam(defaultValue = "Single Family", required = false) String propertyType,
                                               @RequestParam(defaultValue = "", required = false) String city,
                                               @RequestParam(defaultValue = "", required = false) String state,
                                               @RequestParam(defaultValue = "", required = false) String zipCode,
                                               @RequestParam(defaultValue = "-1", required = false) double latitude,
                                               @RequestParam(defaultValue = "-1", required = false) double longitude,
                                               @RequestParam(defaultValue = "-1", required = false) double radius,
                                               @RequestParam(defaultValue = "-1", required = false) double bedrooms,
                                               @RequestParam(defaultValue = "-1", required = false) double bathrooms,
                                               @RequestParam(defaultValue = "Active", required = false) String status,
                                               @RequestParam(defaultValue = "0", required = false) Integer daysOld) {
        GetSaleListingsExternalServiceQuery query = new GetSaleListingsExternalServiceQuery(address, propertyType, city, state, zipCode, latitude, longitude, radius, bedrooms, bathrooms, status, daysOld);
        SaleListingResponse response = mediator.send(query);
        return response;
    }
}
