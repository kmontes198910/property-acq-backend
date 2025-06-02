package com.kynsoft.propertyacqcenter.application.response.rentcast;

import com.kynsof.share.core.domain.bus.query.IResponse;
import java.io.Serializable;
import lombok.Data;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PropertyResponse implements IResponse, Serializable {
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
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Features implements Serializable {
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
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TaxAssessment  implements Serializable {
        private int year;
        private int value;
        private Integer land;
        private Integer improvements;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PropertyTax  implements Serializable {
        private int year;
        private int total;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Owner  implements Serializable {
        private List<String> names;
        private String type;
        private MailingAddress mailingAddress;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MailingAddress  implements Serializable {
        private String id;
        private String formattedAddress;
        private String addressLine1;
        private String addressLine2;
        private String city;
        private String state;
        private String zipCode;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HOA  implements Serializable {
        private Integer fee;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class History  implements Serializable {
        private String event;
        private String date;
        private Integer price;
    }
}