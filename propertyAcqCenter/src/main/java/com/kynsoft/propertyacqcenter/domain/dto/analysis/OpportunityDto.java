package com.kynsoft.propertyacqcenter.domain.dto.analysis;

import com.kynsoft.propertyacqcenter.domain.dto.AnalysisDto;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OpportunityDto {
    private UUID id;
    private String linkedProperties;
    private Double estWholesalePrice;
    private Double monthlyRent;
    private Double grossYield;
    private AnalysisDto analysis;
}
