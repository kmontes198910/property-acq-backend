package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.query.dashboardRequest.EstimateEnum;
import com.kynsoft.propertyacqcenter.application.query.dashboardRequest.GetDashboardInfoQuery;
import com.kynsoft.propertyacqcenter.application.query.estimateValue.getEstimateValueExternalService.GetEstimateValueExternalServiceQuery;
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

    /**
     * Endpoint para obtener los detalles de una propiedad por dirección.
     * Ejemplo: GET /api/rentcast/property?address=2537 NW 116 Terrace, Coral
     * Spring, Florida
     *
     * @param address
     * @return
     */
    @GetMapping("/property")
    public ResponseEntity<PropertyResponse> getPropertyDetails(@RequestParam String address) {
        GetPropertyDetailsExternalServiceQuery query = new GetPropertyDetailsExternalServiceQuery(address);
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
    public ResponseEntity<EstimatedValueResponse> valueDetails(@RequestParam String address) {
        GetEstimateValueExternalServiceQuery query = new GetEstimateValueExternalServiceQuery(address);
        EstimatedValueResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/rent/details")
    public ResponseEntity<RentEstimateResponse> rentDetails(@RequestParam String address) {
        GetRentEstimateExternalServiceQuery query = new GetRentEstimateExternalServiceQuery(address);
        RentEstimateResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/sale")
    public SaleListingResponse getSaleListings(@RequestParam String address) {
        GetEstimateValueExternalServiceQuery query = new GetEstimateValueExternalServiceQuery(address);
        SaleListingResponse response = mediator.send(query);
        return response;
    }
}
