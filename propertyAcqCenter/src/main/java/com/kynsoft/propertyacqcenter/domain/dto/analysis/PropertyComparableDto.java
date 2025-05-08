package com.kynsoft.propertyacqcenter.domain.dto.analysis;

import com.kynsoft.propertyacqcenter.domain.dto.AnalysisDto;
import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PropertyComparableDto {
    private UUID id;
    private String formattedAddress;
    private String propertyType;
    private Date lastSeenDate;
    private Integer squareFootage;
    private Integer lotSize;
    private Integer yearBuilt;
    private Double price;
    private Double latitude;
    private Double longitude;
    private AnalysisDto analysis;
}
