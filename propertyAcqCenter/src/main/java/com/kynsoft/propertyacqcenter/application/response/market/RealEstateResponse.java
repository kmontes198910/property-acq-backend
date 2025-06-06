package com.kynsoft.propertyacqcenter.application.response.market;

import com.kynsof.share.core.domain.bus.query.IResponse;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RealEstateResponse implements IResponse, Serializable {

    private String id;
    private String zipCode;
    private SaleData saleData;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SaleData implements Serializable {

        private String lastUpdatedDate;
        private double averagePrice;
        private double medianPrice;
        private double minPrice;
        private double maxPrice;
        private double averagePricePerSquareFoot;
        private double medianPricePerSquareFoot;
        private double minPricePerSquareFoot;
        private double maxPricePerSquareFoot;
        private double averageSquareFootage;
        private double medianSquareFootage;
        private double minSquareFootage;
        private double maxSquareFootage;
        private double averageDaysOnMarket;
        private double medianDaysOnMarket;
        private double minDaysOnMarket;
        private double maxDaysOnMarket;
        private int newListings;
        private int totalListings;
        private List<PropertyTypeData> dataByPropertyType;
        private List<BedroomsData> dataByBedrooms;
        private Map<String, HistoricalData> history;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class PropertyTypeData implements Serializable {

        private String propertyType;
        private double averagePrice;
        private double medianPrice;
        private double minPrice;
        private double maxPrice;
        private Double averagePricePerSquareFoot;
        private Double medianPricePerSquareFoot;
        private Double minPricePerSquareFoot;
        private Double maxPricePerSquareFoot;
        private Double averageSquareFootage;
        private Double medianSquareFootage;
        private Double minSquareFootage;
        private Double maxSquareFootage;
        private double averageDaysOnMarket;
        private double medianDaysOnMarket;
        private double minDaysOnMarket;
        private double maxDaysOnMarket;
        private int newListings;
        private int totalListings;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class BedroomsData implements Serializable {

        private int bedrooms;
        private double averagePrice;
        private double medianPrice;
        private double minPrice;
        private double maxPrice;
        private Double averagePricePerSquareFoot;
        private Double medianPricePerSquareFoot;
        private Double minPricePerSquareFoot;
        private Double maxPricePerSquareFoot;
        private Double averageSquareFootage;
        private Double medianSquareFootage;
        private Double minSquareFootage;
        private Double maxSquareFootage;
        private double averageDaysOnMarket;
        private double medianDaysOnMarket;
        private double minDaysOnMarket;
        private double maxDaysOnMarket;
        private int newListings;
        private int totalListings;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class HistoricalData implements Serializable {

        private String date;
        private double averagePrice;
        private double medianPrice;
        private double minPrice;
        private double maxPrice;
        private Double averagePricePerSquareFoot;
        private Double medianPricePerSquareFoot;
        private Double minPricePerSquareFoot;
        private Double maxPricePerSquareFoot;
        private Double averageSquareFootage;
        private Double medianSquareFootage;
        private Double minSquareFootage;
        private Double maxSquareFootage;
        private double averageDaysOnMarket;
        private double medianDaysOnMarket;
        private double minDaysOnMarket;
        private double maxDaysOnMarket;
        private int newListings;
        private int totalListings;
        private List<PropertyTypeData> dataByPropertyType;
        private List<BedroomsData> dataByBedrooms;
    }

}
