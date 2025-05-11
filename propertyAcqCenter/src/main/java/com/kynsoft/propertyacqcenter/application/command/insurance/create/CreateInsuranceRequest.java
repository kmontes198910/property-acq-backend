package com.kynsoft.propertyacqcenter.application.command.insurance.create;

import com.kynsoft.propertyacqcenter.domain.enums.InsuranceType;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateInsuranceRequest {

    private InsuranceType insuranceType;
    private String document;
    private UUID legalEntity;
    private String fileName;
}
