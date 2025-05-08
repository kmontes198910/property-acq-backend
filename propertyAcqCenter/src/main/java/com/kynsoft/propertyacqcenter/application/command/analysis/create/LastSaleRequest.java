package com.kynsoft.propertyacqcenter.application.command.analysis.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LastSaleRequest {
    private String publicRecord;
    private String mls;
    private String documentType;
}
