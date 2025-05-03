package com.kynsoft.propertyacqcenter.application.response.property;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.enums.PropertyType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PropertyDasboardResponse implements IResponse {

    private String id;
    private String formattedAddress;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zipCode;
    private String county;//
    private PropertyType propertyType;//
    private int bedrooms;//
    private int bathrooms;//
    private int squareFootage;//
    private int lotSize;//
    private int yearBuilt;//
    private String assessorID;//APN
    private String lastSaleDate;
    private double lastSalePrice;
    private boolean ownerOccupied;//
    private List<PropertyImageResponse> images;
}
