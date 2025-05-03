package com.kynsoft.propertyacqcenter.infrastructure.services.mock;

import com.kynsoft.propertyacqcenter.domain.enums.PropertyType;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.property.dto.FeaturesDto;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.property.dto.HoaDto;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.property.dto.OwnerDto;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.property.dto.PropertyAddressDto;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.property.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.property.dto.PropertyTaxDto;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.property.dto.SaleHistoryDto;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.property.dto.TaxAssessmentsDto;
import com.kynsoft.propertyacqcenter.domain.dto.property.saleListing.ListingAgentDto;
import com.kynsoft.propertyacqcenter.domain.dto.property.saleListing.ListingHistoryDto;
import com.kynsoft.propertyacqcenter.domain.dto.property.saleListing.ListingOfficeDto;
import com.kynsoft.propertyacqcenter.domain.dto.property.saleListing.SaleListingDto;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.estimate.dto.ComparablePropertyDto;
import com.kynsoft.propertyacqcenter.infrastructure.services.http.estimate.dto.EstimatedValueDto;
import com.kynsoft.propertyacqcenter.domain.enums.Status;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RentCastServiceMockImpl {

    public RentCastServiceMockImpl() {
    }

    public List<PropertyDto> getPropertyDetails() {
        return List.of(this.createMockProperty());
    }

    public EstimatedValueDto getRentEstimateDetail() {
        EstimatedValueDto estimatedValueDto = new EstimatedValueDto();
        UUID id = UUID.randomUUID();
        estimatedValueDto.setId(id);
        estimatedValueDto.setPrice(1670);
        estimatedValueDto.setPriceRangeLow(1630);
        estimatedValueDto.setPriceRangeHigh(1710);
        estimatedValueDto.setLatitude(29.475962);
        estimatedValueDto.setLongitude(-98.351442);

        List<ComparablePropertyDto> comparables = new ArrayList<>();
        comparables.add(new ComparablePropertyDto(
                UUID.randomUUID(), 
                id,
                "5711 Leon Pl, San Antonio, TX 78244", 
                "5711 Leon Pl", 
                null, 
                "San Antonio", 
                "TX", 
                "78244", 
                "Bexar", 
                29.481808, 
                -98.346176, 
                PropertyType.APARTMENT, 
                4, 
                2.0, 
                1627, 
                4617, 
                2021, 
                1690, 
                "Standard", 
                "2024-04-03T00:00:00.000Z", 
                "2024-05-26T00:00:00.000Z", 
                "2024-05-25T13:11:55.018Z", 
                111, 
                0.5139, 
                88, 
                0.9879
        ));
        comparables.add(new ComparablePropertyDto(
                UUID.randomUUID(), 
                id,
                "5726 Leon Pl, San Antonio, TX 78244", 
                "5726 Leon Pl", 
                null, 
                "San Antonio", 
                "TX", 
                "78244", 
                "Bexar", 
                29.482164, 
                -98.345566, 
                PropertyType.APARTMENT, 
                4, 
                2.0, 
                1627, 
                4966, 
                2020, 
                1650, 
                "Standard", 
                "2024-04-03T00:00:00.000Z", 
                "2024-05-26T00:00:00.000Z", 
                "2024-05-25T13:11:55.018Z", 
                365, 
                0.5561, 
                113, 
                0.9872
        ));
        comparables.add(new ComparablePropertyDto(
                UUID.randomUUID(), 
                id,
                "5713 Verracio Ct, San Antonio, TX 78244", 
                "5713 Verracio Ct", 
                null, 
                "San Antonio", 
                "TX", 
                "78244", 
                "Bexar", 
                29.481749, 
                -98.345222, 
                PropertyType.APARTMENT, 
                4, 
                2.0, 
                1627, 
                4922, 
                2021, 
                1725, 
                "Standard", 
                "2024-04-03T00:00:00.000Z", 
                "2024-05-26T00:00:00.000Z", 
                "2024-05-25T13:11:55.018Z", 
                152, 
                0.5482, 
                259, 
                0.9869
        ));

        estimatedValueDto.setComparables(comparables);
        return estimatedValueDto;
    }

    public EstimatedValueDto getEstimatedValueDetail() {
        EstimatedValueDto estimatedValueDto = new EstimatedValueDto();
        UUID id = UUID.randomUUID();
        estimatedValueDto.setId(id);
        estimatedValueDto.setPrice(221000);
        estimatedValueDto.setPriceRangeLow(208000);
        estimatedValueDto.setPriceRangeHigh(233000);
        estimatedValueDto.setLatitude(29.475962);
        estimatedValueDto.setLongitude(-98.351442);

        List<ComparablePropertyDto> comparables = new ArrayList<>();
        comparables.add(new ComparablePropertyDto(
                UUID.randomUUID(), 
                id,
                "5014 Fern Lk, San Antonio, TX 78244", 
                "5014 Fern Lk", 
                null, 
                "San Antonio", 
                "TX", 
                "78244", 
                "Bexar", 
                29.471777, 
                -98.350172, 
                PropertyType.APARTMENT, 
                4, 
                2.0, 
                1747, 
                6316, 
                1986, 
                229900, 
                "Standard", 
                "2024-04-03T00:00:00.000Z", 
                "2024-05-26T00:00:00.000Z", 
                "2024-05-25T13:11:55.018Z", 
                53, 
                0.2994, 
                127, 
                0.9822
        ));
        comparables.add(new ComparablePropertyDto(
                UUID.randomUUID(), 
                id,
                "6807 Indian Lake Dr, San Antonio, TX 78244", 
                "6807 Indian Lake Dr", 
                null, 
                "San Antonio", 
                "TX", 
                "78244", 
                "Bexar", 
                29.477682, 
                -98.354718, 
                PropertyType.APARTMENT, 
                4, 
                2.0, 
                1786, 
                7841, 
                1972, 
                199000, 
                "Standard", 
                "2024-04-03T00:00:00.000Z", 
                "2024-05-26T00:00:00.000Z", 
                "2024-05-25T13:11:55.018Z", 
                46, 
                0.2304, 
                33, 
                0.9811
        ));
        comparables.add(new ComparablePropertyDto(
                UUID.randomUUID(), 
                id,
                "6730 Stone Lake Dr, San Antonio, TX 78244", 
                "6730 Stone Lake Dr", 
                null, 
                "San Antonio", 
                "TX", 
                "78244", 
                "Bexar", 
                29.477216, 
                -98.355968, 
                PropertyType.APARTMENT, 
                4, 
                2.0, 
                1810, 
                8146, 
                1977, 
                215000, 
                "Standard", 
                "2024-04-03T00:00:00.000Z", 
                "2024-05-26T00:00:00.000Z", 
                "2024-05-25T13:11:55.018Z", 
                35, 
                0.286, 
                73, 
                0.9782
        ));

        estimatedValueDto.setComparables(comparables);
        return estimatedValueDto;
    }

    public List<SaleListingDto> getSaleListings() {
        List<SaleListingDto> saleListingDtos = new ArrayList<>();
        SaleListingDto property = new SaleListingDto();

        // Configurar datos básicos
        property.setId("3821-Hargis-St,-Austin,-TX-78723");
        property.setFormattedAddress("3821 Hargis St, Austin, TX 78723");
        property.setAddressLine1("3821 Hargis St");
        property.setCity("Austin");
        property.setState("TX");
        property.setZipCode("78723");
        property.setCounty("Travis");
        property.setLatitude(30.290643);
        property.setLongitude(-97.701547);
        property.setPropertyType(PropertyType.APARTMENT);
        property.setBedrooms(4);
        property.setBathrooms(2);
        property.setSquareFootage(2345);
        property.setLotSize(3284);
        property.setYearBuilt(2008);

        // Configurar HOA
        HoaDto hoa = new HoaDto();
        hoa.setFee(65);
        property.setHoa(hoa);

        // Configurar estado y precio
        property.setStatus(Status.ACTIVE);
        property.setPrice(899000);
        property.setListingType("Standard");
        property.setListedDate("2024-06-24T00:00:00.000");
        property.setCreatedDate("2021-06-25T00:00:00.000");
        property.setLastSeenDate("2024-09-30T13:11:47.157");
        property.setDaysOnMarket(99);
        property.setMlsName("UnlockMLS");
        property.setMlsNumber("5519228");

        // Configurar agente
        ListingAgentDto agent = new ListingAgentDto();
        agent.setName("Jennifer Welch");
        agent.setPhone("5124313110");
        agent.setEmail("jennifer@gottesmanresidential.com");
        agent.setWebsite("https://www.gottesmanresidential.com");
        property.setListingAgent(agent);

        // Configurar oficina
        ListingOfficeDto office = new ListingOfficeDto();
        office.setName("Gottesman Residential R.E.");
        office.setPhone("5124512422");
        office.setEmail("nataliem@gottesmanresidential.com");
        office.setWebsite("https://www.gottesmanresidential.com");
        property.setListingOffice(office);

        // Configurar historial
        Map<String, ListingHistoryDto> history = new HashMap<>();
        ListingHistoryDto listing = new ListingHistoryDto();
        listing.setEvent("Sale Listing");
        listing.setPrice(899000);
        listing.setListingType("Standard");
        listing.setListedDate("2024-06-24T00:00:00.000");
        listing.setDaysOnMarket(99);
        history.put("2024-06-24", listing);
        property.setHistory(history);

        saleListingDtos.add(property);
        return saleListingDtos;
    }

    private PropertyDto createMockProperty() {
        // Crear la propiedad principal
        PropertyDto property = new PropertyDto();

        // Configurar datos básicos de la propiedad
        UUID id = UUID.randomUUID();
        property.setId(id.toString());
        property.setFormattedAddress("5500 Grand Lake Dr, San Antonio, TX 78244");
        property.setAddressLine1("5500 Grand Lake Dr");
        property.setCity("San Antonio");
        property.setState("TX");
        property.setZipCode("78244");
        property.setCounty("Bexar");
        property.setLatitude(29.476011);
        property.setLongitude(-98.351454);
        property.setPropertyType(PropertyType.APARTMENT);
        property.setBedrooms(3);
        property.setBathrooms(2);
        property.setSquareFootage(1878);
        property.setLotSize(8843);
        property.setYearBuilt(1973);
        property.setAssessorID("05076-103-0500");
        property.setLegalDescription("CB 5076A BLK 3 LOT 50");
        property.setSubdivision("CONV A/S CODE");
        property.setZoning("RH");

        // Configurar fecha de última venta
        property.setLastSaleDate("2025-04-29T12:36:08.365");
        property.setLastSalePrice(185000);

        // Configurar HOA
        HoaDto hoa = new HoaDto();
        hoa.setFee(175);
        property.setHoa(hoa);

        // Configurar características
        FeaturesDto features = new FeaturesDto();
        features.setArchitectureType("Contemporary");
        features.setCooling(true);
        features.setCoolingType("Central");
        features.setExteriorType("Wood");
        features.setFireplace(true);
        features.setFireplaceType("Masonry");
        features.setFloorCount(1);
        features.setFoundationType("Slab / Mat / Raft");
        features.setGarage(true);
        features.setGarageSpaces(2);
        features.setGarageType("Garage");
        features.setHeating(true);
        features.setHeatingType("Forced Air");
        features.setPool(true);
        features.setPoolType("Concrete");
        features.setRoofType("Asphalt");
        features.setRoomCount(5);
        features.setUnitCount(1);
        features.setViewType("City");
        property.setFeatures(features);

        Map<String, TaxAssessmentsDto> taxAssessments = new HashMap<>();
        taxAssessments.put("2019", new TaxAssessmentsDto(id, 2019, 135430, 23450, 111980));
        taxAssessments.put("2020", new TaxAssessmentsDto(id, 2020, 142610, 23450, 119160));
        taxAssessments.put("2021", new TaxAssessmentsDto(id, 2021, 197600, 49560, 148040));
        taxAssessments.put("2022", new TaxAssessmentsDto(id, 2022, 225790, 59380, 166410));
        property.setTaxAssessments(taxAssessments);

        Map<String, PropertyTaxDto> propertyTaxes = new HashMap<>();
        propertyTaxes.put("2019", new PropertyTaxDto());
        propertyTaxes.put("2020", new PropertyTaxDto(2020, 3023));
        propertyTaxes.put("2021", new PropertyTaxDto(2021, 3455));
        propertyTaxes.put("2022", new PropertyTaxDto(2022, 4091));
        propertyTaxes.put("2023", new PropertyTaxDto(2023, 4201));
        property.setPropertyTaxes(propertyTaxes);

        Map<String, SaleHistoryDto> history = new HashMap<>();
        history.put("2004-06-16", new SaleHistoryDto("Sale", "2025-04-29T12:36:08.365", 95000));
        history.put("2017-10-19", new SaleHistoryDto("Sale", "2025-04-29T12:36:08.365", 185000));
        property.setHistory(history);

        OwnerDto owner = new OwnerDto();
        owner.setNames(List.of("Michael Smith"));
        owner.setType("Individual");

        PropertyAddressDto mailingAddress = new PropertyAddressDto();
        mailingAddress.setId("149-Weaver-Blvd,---264,-Weaverville,-NC-28787");
        mailingAddress.setFormattedAddress("149 Weaver Blvd, # 264, Weaverville, NC 28787");
        mailingAddress.setAddressLine1("149 Weaver Blvd");
        mailingAddress.setAddressLine2("# 264");
        mailingAddress.setCity("Weaverville");
        mailingAddress.setState("NC");
        mailingAddress.setZipCode("28787");
        owner.setMailingAddress(mailingAddress);

        property.setOwner(owner);
        return property;
    }
}
