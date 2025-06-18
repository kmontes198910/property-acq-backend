package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.create;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateAdquisitionPropertyRequest {

    private UUID buyer;
    private String property;
    private UUID contact;
}
