package com.kynsoft.propertyacqcenter.infrastructure.entity.embedded.adquisitionProperty;

import com.kynsoft.propertyacqcenter.infrastructure.entity.AdquisitionProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "adquisition_property_hoa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdquisitionPropertyHoa {

    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "adquisition_property_id")
    private AdquisitionProperty adquisitionProperty; // Relación bidireccional

    @Column(name = "hoa_total_units", nullable = true)
    private String hoaTotalUnits;

    @Column(name = "hoa_declaration_of_condominium", nullable = true)
    private String hoaDeclarationOfCondominium;

    @Column(name = "hoa_condominium_rider", nullable = true)
    private String hoaCondominiumRider;

    @Column(name = "hoa_bylaws", nullable = true)
    private String hoaBylaws;

    @Column(name = "hoa_latest_approved_budget", nullable = true)
    private String hoaLatestApprovedBudget;

    @Column(name = "hoa_reserve_study", nullable = true)
    private String hoaReserveStudy;

    @Column(name = "hoa_current_special_assessment_disclosure", nullable = true)
    private String hoaCurrentSpecialAssessmentDisclosure;

    @Column(name = "hoa_pending_lawsuits", nullable = true)
    private String hoaPendingLawsuits;

    @Column(name = "hoa_delinquency_report", nullable = true)
    private String hoaDelinquencyReport;

    @Column(name = "hoa_parking_assignment", nullable = true)
    private String hoaParkingAssignment;

    @Column(name = "hoa_condo_questionnaire_form", nullable = true)
    private String hoaCondoQuestionnaireForm;

    @Column(name = "buyer_credit_report", nullable = true)
    private String buyerCreditReport;

    @Column(name = "hoa_validator_website", nullable = true)
    private String hoaValidatorWebsite;

    @Column(name = "hoa_application_link", nullable = true)
    private String hoaApplicationLink;
}
