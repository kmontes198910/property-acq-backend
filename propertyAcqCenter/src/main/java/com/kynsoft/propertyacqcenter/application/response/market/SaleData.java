package com.kynsoft.propertyacqcenter.application.response.market;

import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SaleData {
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
