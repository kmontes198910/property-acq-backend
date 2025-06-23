package com.kynsoft.propertyacqcenter.infrastructure.entity.embedded.company;

import com.kynsoft.propertyacqcenter.domain.dto.embedded.company.TitleCompanyDto;
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
public class TitleCompany {
    @Column(name = "title_review")
    private String titleReview;

    @Column(name = "title_commitment")
    private String titleCommitment;

    @Column(name = "copy_of_last_recorded_deed")
    private String copyOfLastRecordedDeed;

    @Column(name = "legal_description_of_the_property")
    private String legalDescriptionOfTheProperty;

    @Column(name = "existing_title_policy")
    private Boolean existingTitlePolicy;

    @Column(name = "copies_of_any_existing")
    private Boolean copiesOfAnyExisting;

    @Column(name = "tax_certificates")
    private String taxCertificates;

    @Column(name = "ucc_search_results")
    private String uccSearchResults;

    @Column(name = "old_title_insurance_policy")
    private String oldTitleInsurancePolicy;

    public TitleCompany(TitleCompanyDto dto) {
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
