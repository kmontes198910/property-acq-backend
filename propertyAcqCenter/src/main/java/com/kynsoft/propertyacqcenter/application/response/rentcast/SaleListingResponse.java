package com.kynsoft.propertyacqcenter.application.response.rentcast;

import com.kynsof.share.core.domain.bus.query.IResponse;
import java.util.Map;
import lombok.Data;

@Data
public class SaleListingResponse implements IResponse {

    private String id;
    private String formattedAddress;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zipCode;
    private String county;
    private double latitude;
    private double longitude;
    private String propertyType;
    private int bedrooms;
    private double bathrooms;
    private int squareFootage;
    private int lotSize;
    private int yearBuilt;
    private HOA hoa;
    private String status;
    private double price;
    private String listingType;
    private String listedDate;
    private String removedDate;
    private String createdDate;
    private String lastSeenDate;
    private int daysOnMarket;
    private String mlsName;
    private String mlsNumber;
    private ListingAgent listingAgent;
    private ListingOffice listingOffice;
    private Map<String, ListingEvent> history;

    @Data
    public static class HOA {

        private Double fee;
    }

    @Data
    public static class ListingAgent {

        private String name;
        private String phone;
        private String email;
    }

    @Data
    public static class ListingOffice {

        private String name;
        private String phone;
        private String email;
        private String website;
    }

    @Data
    public static class ListingEvent {

        private String event;
        private double price;
        private String listingType;
        private String listedDate;
        private String removedDate;
        private int daysOnMarket;
    }
}
