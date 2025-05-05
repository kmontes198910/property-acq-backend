package com.kynsoft.propertyacqcenter.application.response.rentcast;

import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class PropertyResponse implements IResponse {
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
    private int bedrooms;
    private int bathrooms;
    private int squareFootage;
    private int lotSize;
    private int yearBuilt;
    private String assessorID;
    private String legalDescription;
    private String subdivision;
    private String zoning;
    private String lastSaleDate;
    private int lastSalePrice;
    private HOA hoa;
    private Features features;
    private Map<String, TaxAssessment> taxAssessments;
    private Map<String, PropertyTax> propertyTaxes;
    private Map<String, History> history;
    private Owner owner;
    private boolean ownerOccupied;

    @Data
    public static class Features {
        private boolean cooling;
        private String coolingType;
        private String exteriorType;
        private int floorCount;
        private String foundationType;
        private boolean garage;
        private int garageSpaces;
        private String garageType;
        private boolean pool;
        private String poolType;
        private String roofType;
        private int unitCount;
    }

    @Data
    public static class TaxAssessment {
        private int year;
        private int value;
        private Integer land;
        private Integer improvements;
    }

    @Data
    public static class PropertyTax {
        private int year;
        private int total;
    }

    @Data
    public static class Owner {
        private List<String> names;
        private String type;
        private MailingAddress mailingAddress;
    }

    @Data
    public static class MailingAddress {
        private String id;
        private String formattedAddress;
        private String addressLine1;
        private String addressLine2;
        private String city;
        private String state;
        private String zipCode;
    }

    @Data
    public static class HOA {
        private Integer fee;
    }

    @Data
    public static class History {
        private String event;
        private String date;
        private Integer price;
    }
}