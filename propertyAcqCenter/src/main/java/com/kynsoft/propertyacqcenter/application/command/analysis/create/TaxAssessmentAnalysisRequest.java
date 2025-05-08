package com.kynsoft.propertyacqcenter.application.command.analysis.create;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TaxAssessmentAnalysisRequest {
    private Integer year;
    private Double value;
    private Double land;
    private Double improvements;
}
