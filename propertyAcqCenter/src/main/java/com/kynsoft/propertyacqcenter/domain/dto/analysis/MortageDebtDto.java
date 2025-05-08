package com.kynsoft.propertyacqcenter.domain.dto.analysis;

import com.kynsoft.propertyacqcenter.domain.dto.AnalysisDto;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MortageDebtDto {
    private UUID id;
    private Integer openMortages;
    private Double estimatedBalance;
    private Integer involuntaryLiens;
    private Double involuntaryAmount;
    private AnalysisDto analysis;
}
