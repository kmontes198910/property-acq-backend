package com.kynsoft.propertyacqcenter.application.response.rentcast;

import com.kynsof.share.core.domain.bus.query.IResponse;
import lombok.Data;
import java.util.List;

@Data
public class EstimatedValueResponse implements IResponse {
    private Integer price;
    private Integer priceRangeLow;
    private Integer priceRangeHigh;
    private Double latitude;
    private Double longitude;
    private List<ComparableProperty> comparables;

    @Data
    public static class ComparableProperty {
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
        private Integer price;
        private String listingType;
        private String listedDate;
        private String removedDate;
        private String lastSeenDate;
        private Integer daysOnMarket;
        private Double distance;
        private Integer daysOld;
        private Double correlation;
    }
}