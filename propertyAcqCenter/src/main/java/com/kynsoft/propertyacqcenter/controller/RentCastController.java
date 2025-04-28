package com.kynsoft.propertyacqcenter.controller;

import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.propertyacqcenter.application.command.property.createFromRentCast.CreateRentCastPropertyCommand;
import com.kynsoft.propertyacqcenter.application.command.property.createFromRentCast.CreateRentCastPropertyMessage;
import com.kynsoft.propertyacqcenter.application.response.rentcast.EstimatedValueResponse;
import com.kynsoft.propertyacqcenter.application.response.rentcast.PropertyResponse;
import com.kynsoft.propertyacqcenter.application.response.rentcast.RentEstimateResponse;
import com.kynsoft.propertyacqcenter.application.response.rentcast.SaleListingResponse;
import com.kynsoft.propertyacqcenter.domain.services.IRentCastService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/rentcast")
public class RentCastController {

    private final IRentCastService rentCastService;

    private final IMediator mediator;

    public RentCastController(IRentCastService rentCastService, IMediator mediator) {
        this.rentCastService = rentCastService;
        this.mediator = mediator;
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
    public List<PropertyResponse> getFakeProperty(
//            @RequestParam String address
    ) {
        PropertyResponse property = new PropertyResponse();

        property.setId("2537-Nw-116th-Ter,-Coral-Springs,-FL-33065");
        property.setFormattedAddress("2537 Nw 116th Ter, Coral Springs, FL 33065");
        property.setAddressLine1("2537 Nw 116th Ter");
        property.setCity("Coral Springs");
        property.setState("FL");
        property.setZipCode("33065");
        property.setCounty("Broward");
        property.setLatitude(26.262567);
        property.setLongitude(-80.280893);
        property.setPropertyType("Single Family");
        property.setBedrooms(4);
        property.setBathrooms(2);
        property.setSquareFootage(2047);
        property.setLotSize(13733);
        property.setYearBuilt(1974);
        property.setAssessorID("48-41-20-03-1810");
        property.setLegalDescription("CORAL SPRINGS COUNTRY CLUB WEST 70-41 B LOT 9 BLK H");
        property.setSubdivision("CORAL SPRINGS COUNTRY CLUB WEST");
        property.setZoning("RS-3");
        property.setLastSaleDate("1980-04-01T00:00:00.000Z");
        property.setLastSalePrice(53714);
        property.setOwnerOccupied(true);

        // Features
        PropertyResponse.Features features = new PropertyResponse.Features();
        features.setCooling(true);
        features.setCoolingType("Central");
        features.setExteriorType("Stucco");
        features.setFloorCount(1);
        features.setFoundationType("Slab / Mat / Raft");
        features.setGarage(true);
        features.setGarageSpaces(2);
        features.setGarageType("Garage");
        features.setPool(true);
        features.setPoolType("Pool and Hot Tub / Spa");
        features.setRoofType("Tile");
        features.setUnitCount(1);
        property.setFeatures(features);

        // Tax Assessments
        Map<String, PropertyResponse.TaxAssessment> assessments = new HashMap<>();
        PropertyResponse.TaxAssessment a2021 = new PropertyResponse.TaxAssessment();
        a2021.setYear(2021);
        a2021.setValue(310630);
        assessments.put("2021", a2021);

        PropertyResponse.TaxAssessment a2024 = new PropertyResponse.TaxAssessment();
        a2024.setYear(2024);
        a2024.setValue(339410);
        assessments.put("2024", a2024);

        property.setTaxAssessments(assessments);

        // Property Taxes
        Map<String, PropertyResponse.PropertyTax> taxes = new HashMap<>();
        PropertyResponse.PropertyTax t2022 = new PropertyResponse.PropertyTax();
        t2022.setYear(2022);
        t2022.setTotal(6644);
        taxes.put("2022", t2022);

        PropertyResponse.PropertyTax t2024 = new PropertyResponse.PropertyTax();
        t2024.setYear(2024);
        t2024.setTotal(7212);
        taxes.put("2024", t2024);

        property.setPropertyTaxes(taxes);

        // Owner
        PropertyResponse.Owner owner = new PropertyResponse.Owner();
        owner.setNames(Collections.singletonList("JAMES HANSON"));

        PropertyResponse.MailingAddress mailingAddress = new PropertyResponse.MailingAddress();
        mailingAddress.setId("2537-Nw-116th-Ter,-Coral-Springs,-FL-33065");
        mailingAddress.setFormattedAddress("2537 Nw 116th Ter, Coral Springs, FL 33065");
        mailingAddress.setAddressLine1("2537 Nw 116th Ter");
        mailingAddress.setCity("Coral Springs");
        mailingAddress.setState("FL");
        mailingAddress.setZipCode("33065");

        owner.setMailingAddress(mailingAddress);
        property.setOwner(owner);

        property.setHistory(Map.of(
                "2017-10-19", new PropertyResponse.History() {{
                    setEvent("Sale");
                    setDate("2017-10-19T00:00:00.000Z");
                    setPrice(185000);
                }}, "2004-06-16", new PropertyResponse.History() {{
                    setEvent("Sale");
                    setDate("2004-06-16T00:00:00.000Z");
                    setPrice(95000);
                }}
        ));

        return Collections.singletonList(property);
    }

    @GetMapping("/value/fake")
    public EstimatedValueResponse getFakeEstimatedValue(
//            @RequestParam String address
    ) {
        EstimatedValueResponse response = new EstimatedValueResponse();
        response.setPrice(554000);
        response.setPriceRangeLow(358000);
        response.setPriceRangeHigh(750000);
        response.setLatitude(26.2625752);
        response.setLongitude(-80.2809417);

        List<EstimatedValueResponse.ComparableProperty> comparables = new ArrayList<>();

        EstimatedValueResponse.ComparableProperty c1 = new EstimatedValueResponse.ComparableProperty();
        c1.setId("2537-Nw-116th-Ter,-Coral-Springs,-FL-33065");
        c1.setFormattedAddress("2537 Nw 116th Ter, Coral Springs, FL 33065");
        c1.setAddressLine1("2537 Nw 116th Ter");
        c1.setCity("Coral Springs");
        c1.setState("FL");
        c1.setZipCode("33065");
        c1.setCounty("Broward");
        c1.setLatitude(26.262506);
        c1.setLongitude(-80.2809);
        c1.setPropertyType("Single Family");
        c1.setBedrooms(4);
        c1.setBathrooms(2.0);
        c1.setSquareFootage(2047);
        c1.setLotSize(13733);
        c1.setYearBuilt(1974);
        c1.setPrice(570500);
        c1.setListingType("Standard");
        c1.setListedDate("2025-02-28T00:00:00.000Z");
        c1.setRemovedDate(null);
        c1.setLastSeenDate("2025-04-01T12:55:05.069Z");
        c1.setDaysOnMarket(33);
        c1.setDistance(0.0054);
        c1.setDaysOld(1);
        c1.setCorrelation(0.9997);
        comparables.add(c1);

        // Ejemplo de un segundo comparable. Puedes agregar más si deseas.
        EstimatedValueResponse.ComparableProperty c2 = new EstimatedValueResponse.ComparableProperty();
        c2.setId("2620-Nw-115th-Ter,-Coral-Springs,-FL-33065");
        c2.setFormattedAddress("2620 Nw 115th Ter, Coral Springs, FL 33065");
        c2.setAddressLine1("2620 Nw 115th Ter");
        c2.setCity("Coral Springs");
        c2.setState("FL");
        c2.setZipCode("33065");
        c2.setCounty("Broward");
        c2.setLatitude(26.264272);
        c2.setLongitude(-80.280577);
        c2.setPropertyType("Single Family");
        c2.setBedrooms(5);
        c2.setBathrooms(3.0);
        c2.setSquareFootage(3000);
        c2.setLotSize(20422);
        c2.setYearBuilt(1973);
        c2.setPrice(799000);
        c2.setListingType("Standard");
        c2.setListedDate("2025-03-27T00:00:00.000Z");
        c2.setRemovedDate(null);
        c2.setLastSeenDate("2025-04-01T10:39:26.994Z");
        c2.setDaysOnMarket(6);
        c2.setDistance(0.1195);
        c2.setDaysOld(1);
        c2.setCorrelation(0.994);
        comparables.add(c2);

        // Puedes seguir agregando más comparables si lo necesitas
        response.setComparables(comparables);

        return response;
    }

    @GetMapping("/rent/fake")
    public RentEstimateResponse getFakeRentEstimate() {
        RentEstimateResponse response = new RentEstimateResponse();
        response.setRent(2750);
        response.setRentRangeLow(1980);
        response.setRentRangeHigh(3520);
        response.setLatitude(26.2625752);
        response.setLongitude(-80.2809417);

        List<RentEstimateResponse.ComparableRentProperty> comparables = new ArrayList<>();

        RentEstimateResponse.ComparableRentProperty p1 = new RentEstimateResponse.ComparableRentProperty();
        p1.setId("11612-Nw-25th-Ct,-Unit-202,-Coral-Springs,-FL-33065");
        p1.setFormattedAddress("11612 Nw 25th Ct, Unit 202, Coral Springs, FL 33065");
        p1.setAddressLine1("11612 Nw 25th Ct");
        p1.setAddressLine2("Unit 202");
        p1.setCity("Coral Springs");
        p1.setState("FL");
        p1.setZipCode("33065");
        p1.setCounty("Broward");
        p1.setLatitude(26.262924);
        p1.setLongitude(-80.281952);
        p1.setPropertyType("Apartment");
        p1.setBedrooms(2);
        p1.setBathrooms(2.0);
        p1.setSquareFootage(830);
        p1.setLotSize(58586);
        p1.setYearBuilt(1984);
        p1.setPrice(2100);
        p1.setListingType("Standard");
        p1.setListedDate("2024-12-04T00:00:00.000Z");
        p1.setRemovedDate(null);
        p1.setLastSeenDate("2025-04-01T05:05:51.846Z");
        p1.setDaysOnMarket(119);
        p1.setDistance(0.0672);
        p1.setDaysOld(1);
        p1.setCorrelation(0.9967);
        comparables.add(p1);

        RentEstimateResponse.ComparableRentProperty p2 = new RentEstimateResponse.ComparableRentProperty();
        p2.setId("11605-Nw-25th-St,-Coral-Springs,-FL-33065");
        p2.setFormattedAddress("11605 Nw 25th St, Coral Springs, FL 33065");
        p2.setAddressLine1("11605 Nw 25th St");
        p2.setAddressLine2(null);
        p2.setCity("Coral Springs");
        p2.setState("FL");
        p2.setZipCode("33065");
        p2.setCounty("Broward");
        p2.setLatitude(26.261981);
        p2.setLongitude(-80.281545);
        p2.setPropertyType("Single Family");
        p2.setBedrooms(3);
        p2.setBathrooms(2.0);
        p2.setSquareFootage(1346);
        p2.setLotSize(5400);
        p2.setYearBuilt(1981);
        p2.setPrice(3100);
        p2.setListingType("Standard");
        p2.setListedDate("2024-12-10T00:00:00.000Z");
        p2.setRemovedDate("2025-01-03T00:00:00.000Z");
        p2.setLastSeenDate("2025-01-02T04:17:04.830Z");
        p2.setDaysOnMarket(24);
        p2.setDistance(0.0556);
        p2.setDaysOld(90);
        p2.setCorrelation(0.9964);
        comparables.add(p2);

        // Puedes seguir agregando más comparables como p3, p4, etc. si deseas completar la lista

        response.setComparables(comparables);
        return response;
    }

    @GetMapping("/sale/fake")
    public List<SaleListingResponse> getFakeSaleListings(
//            @RequestParam String city,
//            @RequestParam String state
    ) {
        List<SaleListingResponse> listings = new ArrayList<>();

        SaleListingResponse listing1 = new SaleListingResponse();
        listing1.setId("8330-Nw-24th-St,-Unit-8330,-Coral-Springs,-FL-33065");
        listing1.setFormattedAddress("8330 Nw 24th St, Unit 8330, Coral Springs, FL 33065");
        listing1.setAddressLine1("8330 Nw 24th St");
        listing1.setAddressLine2("Unit 8330");
        listing1.setCity("Coral Springs");
        listing1.setState("FL");
        listing1.setZipCode("33065");
        listing1.setCounty("Broward");
        listing1.setLatitude(26.258766);
        listing1.setLongitude(-80.236896);
        listing1.setPropertyType("Condo");
        listing1.setBedrooms(2);
        listing1.setBathrooms(2.0);
        listing1.setSquareFootage(1140);
        listing1.setLotSize(null);
        listing1.setYearBuilt(1991);

        SaleListingResponse.HoaDto hoa = new SaleListingResponse.HoaDto();
        hoa.setFee(605.0);
        listing1.setHoa(hoa);

        listing1.setStatus("Active");
        listing1.setPrice(224900.0);
        listing1.setListingType("Standard");
        listing1.setListedDate("2025-01-17T00:00:00.000Z");
        listing1.setRemovedDate(null);
        listing1.setLastSeenDate("2025-04-01T13:41:33.502Z");
        listing1.setDaysOnMarket(75);
        listing1.setMlsName("MiamiMLS");
        listing1.setMlsNumber("A11724562");

        SaleListingResponse.ListingAgentDto agent = new SaleListingResponse.ListingAgentDto();
        agent.setName("Grayson Adler");
        agent.setPhone("9542968044");
        agent.setEmail("grayson@firstfloridarealty.com");
        agent.setWebsite("http://www.firstfloridarealty.com");
        listing1.setListingAgent(agent);

        SaleListingResponse.ListingOfficeDto office = new SaleListingResponse.ListingOfficeDto();
        office.setName("COMPASS");
        office.setPhone("3058512820");
        office.setEmail("brokerfl@compass.com");
        office.setWebsite("http://www.compass.com");
        listing1.setListingOffice(office);

        listings.add(listing1);

        // Aquí puedes seguir agregando más listings como listing2, listing3, etc.

        return listings;
    }
}