package com.kynsoft.propertyacqcenter.application.query.estimateValue.getRentalListingsExternalService;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetRentalListingsExternalServiceQuery implements IQuery {
    private String address;
    private String propertyType;
    private String city;
    private String state;
    private String zipCode;
    private double latitude;
    private double longitude;
    private double radius;
    private double bedrooms;
    private double bathrooms;
    private String status;
    private int daysOld;
}
