package com.kynsoft.propertyacqcenter.application.command.company.update;

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
public class UpdateTitleCompanyDataRequest {
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
