package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateAdquisitionPropertyRequest {

    private UUID buyer;
    private String property;
    private UUID contact;

    private String buyerNameAndYearVehicle;
    private String buyerLicenseTagNo;
}
