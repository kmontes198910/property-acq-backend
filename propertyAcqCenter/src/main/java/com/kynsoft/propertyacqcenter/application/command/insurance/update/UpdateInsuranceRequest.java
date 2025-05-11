package com.kynsoft.propertyacqcenter.application.command.insurance.update;

import com.kynsoft.propertyacqcenter.domain.enums.InsuranceType;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateInsuranceRequest {

    private InsuranceType insuranceType;
    private String document;
    private UUID legalEntity;
    private String fileName;
}
