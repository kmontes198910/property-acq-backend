package com.kynsoft.propertyacqcenter.domain.dto.embedded.adquisitionProperty;

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
public class AdquisitionPropertyTitleCompanyDto {
    private String lenderTitleInsurance;
    private String ownerTitleInsurance;
}
