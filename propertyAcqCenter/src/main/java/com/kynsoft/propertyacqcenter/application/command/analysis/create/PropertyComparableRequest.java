package com.kynsoft.propertyacqcenter.application.command.analysis.create;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PropertyComparableRequest {
    private String formattedAddress;
    private String propertyType;
    private Date lastSeenDate;
    private Integer squareFootage;
    private Integer lotSize;
    private Integer yearBuilt;
    private Double price;
    private Double latitude;
    private Double longitude;
    private Integer daysOnMarket;
}
