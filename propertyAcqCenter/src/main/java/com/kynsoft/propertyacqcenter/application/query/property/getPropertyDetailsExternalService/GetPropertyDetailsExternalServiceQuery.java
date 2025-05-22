package com.kynsoft.propertyacqcenter.application.query.property.getPropertyDetailsExternalService;

import com.kynsof.share.core.domain.bus.query.IQuery;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetPropertyDetailsExternalServiceQuery implements IQuery {
    private String address;    
    private String city;
    private String state;
    private String propertyType;
    private String zipCode;
    private double latitude;
    private double longitude;
    private double bedrooms;
    private double bathrooms;
}
