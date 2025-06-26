package com.kynsoft.propertyacqcenter.application.command.company.update;

import java.util.UUID;
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
public class UpdateSellerCompanyDataRequest {
    private UUID id;
    private String socialSecurity;
    private String folioParcelNumber;
    private Boolean declareIfForeing;
    private String legalDescription;
    private String lenderName;
    private String loanNumber;
}
