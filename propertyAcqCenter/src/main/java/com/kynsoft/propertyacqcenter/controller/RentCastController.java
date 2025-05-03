package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.query.estimateValue.getEstimateValueExternalService.GetEstimateValueExternalServiceQuery;
import com.kynsoft.propertyacqcenter.application.query.estimateValue.getRentEstimateExternalService.GetRentEstimateExternalServiceQuery;
import com.kynsoft.propertyacqcenter.application.query.property.getPropertyDetailsExternalService.GetPropertyDetailsExternalServiceQuery;
import com.kynsoft.propertyacqcenter.application.response.estimateValue.EstimatedValueResponse;
import com.kynsoft.propertyacqcenter.application.response.property.PropertyDasboardResponse;
import com.kynsoft.propertyacqcenter.application.response.rentcast.SaleListingResponse;
import com.kynsoft.propertyacqcenter.domain.services.IRentCastService;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.property.RentCastPropertyServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/rentcast")
public class RentCastController {

    private final IRentCastService rentCastService;
    private final IMediator mediator;
    private final RentCastPropertyServiceImpl resCastPropertyServiceImpl;

    public RentCastController(IRentCastService rentCastService,
                              RentCastPropertyServiceImpl resCastPropertyServiceImpl,
                              IMediator mediator) {
        this.rentCastService = rentCastService;
        this.resCastPropertyServiceImpl = resCastPropertyServiceImpl;
        this.mediator = mediator;
    }

    /**
     * Endpoint para obtener los detalles de una propiedad por dirección.
     * Ejemplo: GET /api/rentcast/property?address=2537 NW 116 Terrace, Coral Spring, Florida
     * @param address
     * @return 
     */
    @GetMapping("/property")
    public ResponseEntity<PropertyDasboardResponse> getPropertyDetails(@RequestParam String address) {
        GetPropertyDetailsExternalServiceQuery query = new GetPropertyDetailsExternalServiceQuery(address);
        PropertyDasboardResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/value/details")
    public ResponseEntity<EstimatedValueResponse> valueDetails(@RequestParam String address) {
        GetEstimateValueExternalServiceQuery query = new GetEstimateValueExternalServiceQuery(address);
        EstimatedValueResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/rent/details")
    public ResponseEntity<EstimatedValueResponse> rentDetails(@RequestParam String address) {
        GetRentEstimateExternalServiceQuery query = new GetRentEstimateExternalServiceQuery(address);
        EstimatedValueResponse response = mediator.send(query);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/sale")
    public List<SaleListingResponse> getSaleListings(
            @RequestParam String city,
            @RequestParam String state
    ) {
        return rentCastService.getSaleListings(city, state);
    }
}