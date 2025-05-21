package com.kynsoft.propertyacqcenter.application.query.restEstimate.getRentEstimateExternalService;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetRentEstimateExternalServiceQuery implements IQuery {

    private String address;
    private String propertyType;
    private double latitude;
    private double longitude;
    private double bedrooms;
    private double bathrooms;
    private double squareFootage;
    private int daysOld;
}
