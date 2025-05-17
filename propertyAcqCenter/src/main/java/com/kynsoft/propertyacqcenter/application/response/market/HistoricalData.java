package com.kynsoft.propertyacqcenter.application.response.market;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HistoricalData {
    private String date;
    private Double averagePrice;
    private Double medianPrice;
    private Double minPrice;
    private Double maxPrice;
    private Double averagePricePerSquareFoot;
    private Double medianPricePerSquareFoot;
    private Double minPricePerSquareFoot;
    private Double maxPricePerSquareFoot;
    private Double averageSquareFootage;
    private Double medianSquareFootage;
    private Double minSquareFootage;
    private Double maxSquareFootage;
    private Double averageDaysOnMarket;
    private Double medianDaysOnMarket;
    private Double minDaysOnMarket;
    private Double maxDaysOnMarket;
    private Integer newListings;
    private Integer totalListings;
    private List<PropertyTypeData> dataByPropertyType;
    private List<BedroomsData> dataByBedrooms;
}
