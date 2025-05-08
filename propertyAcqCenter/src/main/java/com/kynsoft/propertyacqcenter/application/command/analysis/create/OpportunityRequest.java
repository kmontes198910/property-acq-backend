package com.kynsoft.propertyacqcenter.application.command.analysis.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OpportunityRequest {
    private String linkedProperties;
    private Double estWholesalePrice;
    private Double monthlyRent;
    private Double grossYield;
}
