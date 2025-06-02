package com.kynsoft.propertyacqcenter.domain.dto.analysis;

import com.kynsoft.propertyacqcenter.domain.dto.AnalysisDto;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class TaxAssessmentAnalysisDto {
    private UUID id;
    private Integer year;
    private Double value;
    private Double land;
    private Double improvements;
    private AnalysisDto analysis;
}
