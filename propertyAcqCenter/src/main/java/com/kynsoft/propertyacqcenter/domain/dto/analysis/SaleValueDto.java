package com.kynsoft.propertyacqcenter.domain.dto.analysis;

import com.kynsoft.propertyacqcenter.domain.dto.AnalysisDto;
import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class SaleValueDto {
    private UUID id;
    private Double estimatedValue;
    private Date lastYear;
    private AnalysisDto analysis;
}
