package com.kynsoft.propertyacqcenter.application.response.company;

import com.kynsof.share.core.domain.bus.query.IResponse;
import com.kynsoft.propertyacqcenter.domain.dto.embedded.company.TitleCompanyDto;
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
public class TitleCompanyEmbeddedResponse implements IResponse {
    private String titleReview;
    private String titleCommitment;
    private String copyOfLastRecordedDeed;
    private String legalDescriptionOfTheProperty;
    private Boolean existingTitlePolicy;
    private Boolean copiesOfAnyExisting;
    private String taxCertificates;
    private String uccSearchResults;
    private String oldTitleInsurancePolicy;

    public TitleCompanyEmbeddedResponse(TitleCompanyDto dto) {
        this.titleReview = dto.getTitleReview();
        this.titleCommitment = dto.getTitleCommitment();
        this.copyOfLastRecordedDeed = dto.getCopyOfLastRecordedDeed();
        this.legalDescriptionOfTheProperty = dto.getLegalDescriptionOfTheProperty();
        this.existingTitlePolicy = dto.getExistingTitlePolicy();
        this.copiesOfAnyExisting = dto.getCopiesOfAnyExisting();
        this.taxCertificates = dto.getTaxCertificates();
        this.uccSearchResults = dto.getUccSearchResults();
        this.oldTitleInsurancePolicy = dto.getOldTitleInsurancePolicy();
    }

}
