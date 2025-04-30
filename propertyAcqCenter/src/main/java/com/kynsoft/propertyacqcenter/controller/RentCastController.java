package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.property.createFromRentCast.CreateRentCastPropertyCommand;
import com.kynsoft.propertyacqcenter.application.command.property.createFromRentCast.CreateRentCastPropertyMessage;
import com.kynsoft.propertyacqcenter.application.response.rentcast.EstimatedValueResponse;
import com.kynsoft.propertyacqcenter.application.response.rentcast.PropertyResponse;
import com.kynsoft.propertyacqcenter.application.response.rentcast.RentEstimateResponse;
import com.kynsoft.propertyacqcenter.application.response.rentcast.SaleListingResponse;
import com.kynsoft.propertyacqcenter.domain.dto.property.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.dto.property.saleListing.SaleListingDto;
import com.kynsoft.propertyacqcenter.domain.dto.valueEstimate.EstimatedValueDto;
import com.kynsoft.propertyacqcenter.domain.services.IRentCastService;
import com.kynsoft.propertyacqcenter.infrastructure.services.RentCastServiceMockImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/rentcast")
public class RentCastController {

    private final IRentCastService rentCastService;

    private final IMediator mediator;

    private final RentCastServiceMockImpl mock;

    public RentCastController(IRentCastService rentCastService, IMediator mediator, RentCastServiceMockImpl mock) {
        this.rentCastService = rentCastService;
        this.mediator = mediator;
        this.mock = mock;
    }

    /**
     * Endpoint para obtener los detalles de una propiedad por dirección.
     * Ejemplo: GET /api/rentcast/property?address=2537 NW 116 Terrace, Coral Spring, Florida
     */
    @GetMapping("/property")
    public List<PropertyResponse> getPropertyDetails(@RequestParam String address) {
        return rentCastService.getPropertyDetails(address);
    }

    @GetMapping("/value/details")
    public EstimatedValueResponse valueDetails(@RequestParam String address) {
        return rentCastService.getEstimatedValueDetail(address);
    }

    @GetMapping("/rent/details")
    public RentEstimateResponse rentDetails(@RequestParam String address) {
        return rentCastService.getRentEstimateDetail(address);
    }

    @GetMapping("/sale")
    public List<SaleListingResponse> getSaleListings(
            @RequestParam String city,
            @RequestParam String state
    ) {
        return rentCastService.getSaleListings(city, state);
    }

    /**
     * Endpoint para crear propiedades a partir de la respuesta de RentCast.
     * Ejemplo: POST /api/rentcast/property/create
     */
    @PostMapping("/property/create")
    public ResponseEntity<?> createRentCastProperty(@RequestBody List<PropertyResponse> propertyResponse) {
        CreateRentCastPropertyCommand command = new CreateRentCastPropertyCommand(propertyResponse);
        CreateRentCastPropertyMessage message = this.mediator.send(command);
        return ResponseEntity.ok(message);
    }

    /**
     * Endpoint para PROBAR
     */
    @GetMapping("/sales/prueba")
    public List<SaleListingResponse> salesDetailsPrueba() {
        return rentCastService.getSaleListings("", "");
    }

    @GetMapping("/property/fake")
    public List<PropertyDto> getFakeProperty() {
        
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