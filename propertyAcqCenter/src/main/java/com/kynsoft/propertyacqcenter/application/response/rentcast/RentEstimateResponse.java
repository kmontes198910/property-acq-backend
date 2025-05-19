package com.kynsoft.propertyacqcenter.application.response.rentcast;

import com.kynsof.share.core.domain.bus.query.IResponse;
import java.io.Serializable;
import lombok.Data;

import java.util.List;

@Data
public class RentEstimateResponse implements IResponse, Serializable {
    private Integer rent;
    private Integer rentRangeLow;
    private Integer rentRangeHigh;
    private Double latitude;
    private Double longitude;
    private List<ComparableRentProperty> comparables;

    @Data
    public static class ComparableRentProperty implements Serializable {
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