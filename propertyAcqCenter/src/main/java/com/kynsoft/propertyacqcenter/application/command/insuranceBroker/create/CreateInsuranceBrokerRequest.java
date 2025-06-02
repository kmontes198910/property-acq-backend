package com.kynsoft.propertyacqcenter.application.command.insuranceBroker.create;

import java.time.LocalDate;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateInsuranceBrokerRequest {

    private LocalDate closingDate;
    private UUID buyer;//Legal Entity
    private String property;
    private String lenderInfo;
    private String lenderInsurance;
}
