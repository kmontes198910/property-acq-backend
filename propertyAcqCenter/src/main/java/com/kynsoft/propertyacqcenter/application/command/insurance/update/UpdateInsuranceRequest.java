package com.kynsoft.propertyacqcenter.application.command.insurance.update;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateInsuranceRequest {

    private String insuranceType;
    private String document;
    private UUID legalEntity;
    private String fileName;
}
