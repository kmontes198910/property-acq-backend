package com.kynsoft.propertyacqcenter.application.response.market;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PropertyTypeData {
    private String propertyType;
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
}
