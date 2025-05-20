package com.kynsoft.propertyacqcenter.application.response.rentcast;
import com.kynsof.share.core.domain.bus.query.IResponse;
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
    private Double latitude;
    private Double longitude;
    private String propertyType;
    private Integer bedrooms;
    private Double bathrooms;
    private Integer squareFootage;
    private Integer lotSize;
    private Integer yearBuilt;
    private HoaDto hoa;
    private String status;
    private Double price;
    private String listingType;
    private String listedDate;
    private String removedDate;
    private String lastSeenDate;
    private Integer daysOnMarket;
    private String mlsName;
    private String mlsNumber;
    private ListingAgentDto listingAgent;
    private ListingOfficeDto listingOffice;

    @Data
    public static class HoaDto {
        private Double fee;
    }

    @Data
    public static class ListingAgentDto {
        private String name;
        private String phone;
        private String email;
        private String website;
    }

    @Data
    public static class ListingOfficeDto {
        private String name;
        private String phone;
        private String email;
        private String website;
    }
}