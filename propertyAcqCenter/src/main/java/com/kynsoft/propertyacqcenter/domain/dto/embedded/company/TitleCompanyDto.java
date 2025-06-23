package com.kynsoft.propertyacqcenter.domain.dto.embedded.company;

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
public class TitleCompanyDto {
    private String titleReview;
    private String titleCommitment;
    private String copyOfLastRecordedDeed;
    private String legalDescriptionOfTheProperty;
    private Boolean existingTitlePolicy;
    private Boolean copiesOfAnyExisting;
    private String taxCertificates;
    private String uccSearchResults;
    private String oldTitleInsurancePolicy;
}
