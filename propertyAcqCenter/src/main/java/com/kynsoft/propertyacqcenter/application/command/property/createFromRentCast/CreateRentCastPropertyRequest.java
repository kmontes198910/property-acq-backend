package com.kynsoft.propertyacqcenter.application.command.property.createFromRentCast;

import com.kynsoft.propertyacqcenter.application.response.rentcast.PropertyResponse;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateRentCastPropertyRequest {
    private List<PropertyResponse> propertyResponses;
}
