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
public class RentalData {
    private String lastUpdatedDate;
    private double averageRent;
    private double medianRent;
    private double minRent;
    private double maxRent;
    private double averageRentPerSquareFoot;
    private double medianRentPerSquareFoot;
    private double minRentPerSquareFoot;
    private double maxRentPerSquareFoot;
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
