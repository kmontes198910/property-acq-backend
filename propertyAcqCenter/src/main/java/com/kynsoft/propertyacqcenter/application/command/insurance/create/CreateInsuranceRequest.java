package com.kynsoft.propertyacqcenter.application.command.insurance.create;

import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateInsuranceRequest {

    private String insuranceType;
    private String document;
    private UUID legalEntity;
}
