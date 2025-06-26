package com.kynsoft.propertyacqcenter.application.command.company.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSellerCompanyDataRequest {
    private String socialSecurity;
    private String folioParcelNumber;
    private Boolean declareIfForeing;
    private String legalDescription;
    private String lenderName;
    private String loanNumber;
}
