package com.kynsoft.propertyacqcenter.domain.dto.embedded.adquisitionProperty;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdquisitionTitleCompanyDto {
    private String titleCommitment;
}
