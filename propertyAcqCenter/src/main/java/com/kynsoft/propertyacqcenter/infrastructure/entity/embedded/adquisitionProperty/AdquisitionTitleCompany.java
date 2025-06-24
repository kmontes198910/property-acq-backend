package com.kynsoft.propertyacqcenter.infrastructure.entity.embedded.adquisitionProperty;

import com.kynsoft.propertyacqcenter.domain.dto.embedded.adquisitionProperty.AdquisitionTitleCompanyDto;
import jakarta.persistence.Column;
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
public class AdquisitionTitleCompany {
    @Column(name = "title_commitment")
    private String titleCommitment;

    public AdquisitionTitleCompany(AdquisitionTitleCompanyDto dto) {
        this.titleCommitment = dto.getTitleCommitment();
    }

}
