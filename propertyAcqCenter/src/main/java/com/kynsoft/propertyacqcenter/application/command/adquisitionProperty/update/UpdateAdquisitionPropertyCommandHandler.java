package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.AdquisitionPropertyDto;
import com.kynsoft.propertyacqcenter.domain.dto.CompanyContactDto;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.services.IAdquisitionPropertyService;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyContactService;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import org.springframework.stereotype.Component;

@Component
public class UpdateAdquisitionPropertyCommandHandler implements ICommandHandler<UpdateAdquisitionPropertyCommand> {

    private final IAdquisitionPropertyService adquisitionPropertyService;
    private final ILegalEntityService legalEntityService;
    private final IPropertyService propertyService;
    private final ICompanyContactService companyContactService;

    public UpdateAdquisitionPropertyCommandHandler(IAdquisitionPropertyService adquisitionPropertyService, 
                                                   ILegalEntityService legalEntityService,
                                                   IPropertyService propertyService,
                                                   ICompanyContactService companyContactService) {
        this.adquisitionPropertyService = adquisitionPropertyService;
        this.legalEntityService = legalEntityService;
        this.propertyService = propertyService;
        this.companyContactService = companyContactService;
    }

    @Override
    public void handle(UpdateAdquisitionPropertyCommand command) {
        LegalEntityDto legalEntityDto = command.getBuyer() != null ? this.legalEntityService.findById(command.getBuyer()) : null;
        PropertyDto propertyDto = command.getProperty() != null ? this.propertyService.getById(command.getProperty()) : null;
        CompanyContactDto contactDto = command.getContact() != null ? this.companyContactService.findById(command.getContact()) : null;

        adquisitionPropertyService.update(AdquisitionPropertyDto
                .builder()
                .id(command.getId())
                .buyer(legalEntityDto)
                .property(propertyDto)
                .contact(contactDto)
                .buyerLicenseTagNo(command.getBuyerLicenseTagNo())
                .buyerNameAndYearVehicle(command.getBuyerNameAndYearVehicle())
                .dateAndTimeForInspections(command.getDateAndTimeForInspections())
                .instructionsForAccess(command.getInstructionsForAccess())
                .hoaBuyerInterviewDate(command.getHoaBuyerInterviewDate())
                .preferredMoveinDate(command.getPreferredMoveinDate())
                .eSignAuthorization(command.getESignAuthorization())
                .finalWalkthroughDate(command.getFinalWalkthroughDate())
                .wireAccountHolderName(command.getWireAccountHolderName())
                .wireAccountNumber(command.getWireAccountNumber())
                .wireRoutingNumber(command.getWireRoutingNumber())
                .zelleEmailorPhone(command.getZelleEmailorPhone())
                .electricProviderConfirmation(command.getElectricProviderConfirmation())
                .gasServiceConfirmation(command.getGasServiceConfirmation())
                .trashServiceConfirmation(command.getTrashServiceConfirmation())
                .waterSewerSetupConfirmation(command.getWaterSewerSetupConfirmation())

                .uploadGovernmentIssuedId(command.getUploadGovernmentIssuedId() != null ? command.getUploadGovernmentIssuedId().getFilePath() + "|" + command.getUploadGovernmentIssuedId().getFileName() : null)
                .hoaApplicationForm(command.getHoaApplicationForm() != null ? command.getHoaApplicationForm().getFilePath() + "|" + command.getHoaApplicationForm().getFileName() : null)
                .hoaApplicationUpload(command.getHoaApplicationUpload() != null ? command.getHoaApplicationUpload().getFilePath() + "|" + command.getHoaApplicationUpload().getFileName() : null)
                .hoaFinancials(command.getHoaFinancials() != null ? command.getHoaFinancials().getFilePath() + "|" + command.getHoaFinancials().getFileName() : null)
                .hoaRulesRegulations(command.getHoaRulesRegulations() != null ? command.getHoaRulesRegulations().getFilePath() + "|" + command.getHoaRulesRegulations().getFileName() : null)
                .buyerCarRegistration(command.getBuyerCarRegistration() != null ? command.getBuyerCarRegistration().getFilePath() + "|" + command.getBuyerCarRegistration().getFileName() : null)
                .buyerBackgroundCheck(command.getBuyerBackgroundCheck() != null ? command.getBuyerBackgroundCheck().getFilePath() + "|" + command.getBuyerBackgroundCheck().getFileName() : null)
                .commitmentLetter(command.getCommitmentLetter() != null ? command.getCommitmentLetter().getFilePath() + "|" + command.getCommitmentLetter().getFileName() : null)
                .appraisalReport(command.getAppraisalReport() != null ? command.getAppraisalReport().getFilePath() + "|" + command.getAppraisalReport().getFileName() : null)
                .inspectionReport(command.getInspectionReport() != null ? command.getInspectionReport().getFilePath() + "|" + command.getInspectionReport().getFileName() : null)
                .sellerDisclosureForm(command.getSellerDisclosureForm() != null ? command.getSellerDisclosureForm().getFilePath() + "|" + command.getSellerDisclosureForm().getFileName() : null)
                .surveyDocument(command.getSurveyDocument() != null ? command.getSurveyDocument().getFilePath() + "|" + command.getSurveyDocument().getFileName() : null)
                .titleCommitment(command.getTitleCommitment() != null ? command.getTitleCommitment().getFilePath() + "|" + command.getTitleCommitment().getFileName() : null)
                .legalEntityCertificationStatus(command.getLegalEntityCertificationStatus() != null ? command.getLegalEntityCertificationStatus().getFilePath() + "|" + command.getLegalEntityCertificationStatus().getFileName() : null)
                .assignmentOfContract(command.getAssignmentOfContract() != null ? command.getAssignmentOfContract().getFilePath() + "|" + command.getAssignmentOfContract().getFileName() : null)
                .ownerExecutedContract(command.getOwnerExecutedContract() != null ? command.getOwnerExecutedContract().getFilePath() + "|" + command.getOwnerExecutedContract().getFileName() : null)
                .contractAddendum(command.getContractAddendum() != null ? command.getContractAddendum().getFilePath() + "|" + command.getContractAddendum().getFileName() : null)
                .finalSettlementStatement(command.getFinalSettlementStatement() != null ? command.getFinalSettlementStatement().getFilePath() + "|" + command.getFinalSettlementStatement().getFileName() : null)
                .bankStatementRequest(command.getBankStatementRequest() != null ? command.getBankStatementRequest().getFilePath() + "|" + command.getBankStatementRequest().getFileName() : null)
                .warrantyDeed(command.getWarrantyDeed() != null ? command.getWarrantyDeed().getFilePath() + "|" + command.getWarrantyDeed().getFileName() : null)
                .titleInsurance(command.getTitleInsurance() != null ? command.getTitleInsurance().getFilePath() + "|" + command.getTitleInsurance().getFileName() : null)
                .executedClosingDocuments(command.getExecutedClosingDocuments() != null ? command.getExecutedClosingDocuments().getFilePath() + "|" + command.getExecutedClosingDocuments().getFileName() : null)

                .buyerFullLegalName(command.getBuyerFullLegalName())
                .buyerContactEmail(command.getBuyerContactEmail())
                .buyerEntityName(command.getBuyerEntityName())
                .buyerMailingAddress(command.getBuyerMailingAddress())
                .buyerMobilePhoneNumber(command.getBuyerMobilePhoneNumber())
                .hoa4050certificationStatus(command.getHoa4050certificationStatus())
                .hoaValidatorContactName(command.getHoaValidatorContactName())
                .hoaValidatorEmail(command.getHoaValidatorEmail())
                .hoaValidatorPhoneNumber(command.getHoaValidatorPhoneNumber())
                .closingCountdownClock(command.getClosingCountdownClock())
                .contractClosingDate(command.getContractClosingDate())

                .sellerFullName(command.getSellerFullName())
                .sellerEntityName(command.getSellerEntityName())
                .sellerArticlesOfIncorporation(command.getSellerArticlesOfIncorporation() != null ? command.getSellerArticlesOfIncorporation().getFilePath() + "|" + command.getSellerArticlesOfIncorporation().getFileName() : null)
                .sellerCertificateOfGoodStanding(command.getSellerCertificateOfGoodStanding() != null ? command.getSellerCertificateOfGoodStanding().getFilePath() + "|" + command.getSellerCertificateOfGoodStanding().getFileName() : null)
                .sellerOperatingAgreement(command.getSellerOperatingAgreement() != null ? command.getSellerOperatingAgreement().getFilePath() + "|" + command.getSellerOperatingAgreement().getFileName() : null)
                .sellerOwnershipType(command.getSellerOwnershipType())
                .sellerResolutionToSell(command.getSellerResolutionToSell() != null ? command.getSellerResolutionToSell().getFilePath() + "|" + command.getSellerResolutionToSell().getFileName() : null)
                .sellerSocialSecurityNumber(command.getSellerSocialSecurityNumber())
                .sellerMaritalStatus(command.getSellerMaritalStatus())
                .sellerGovernmentId(command.getSellerGovernmentId() != null ? command.getSellerGovernmentId().getFilePath() + "|" + command.getSellerGovernmentId().getFileName() : null)
                .sellerW9Form(command.getSellerW9Form() != null ? command.getSellerW9Form().getFilePath() + "|" + command.getSellerW9Form().getFileName() : null)
                .sellerForeignSeller(command.getSellerForeignSeller())
                .sellerFirptaAffidavit(command.getSellerFirptaAffidavit() != null ? command.getSellerFirptaAffidavit().getFilePath() + "|" + command.getSellerFirptaAffidavit().getFileName() : null)
                .sellerWireAccountHolder(command.getSellerWireAccountHolder())
                .sellerWireAccountNumber(command.getSellerWireAccountNumber())
                .sellerWireRoutingNumber(command.getSellerWireRoutingNumber())
                .zelleContact(command.getZelleContact())
                .titleCompanyRequestForEstoppelLetter(command.getTitleCompanyRequestForEstoppelLetter())
                .titleCompanyEarnestMoneyDepositConfirmation(command.getTitleCompanyEarnestMoneyDepositConfirmation() != null ? command.getTitleCompanyEarnestMoneyDepositConfirmation().getFilePath() + "|" + command.getTitleCompanyEarnestMoneyDepositConfirmation().getFileName() : null)

                .surveyavailable(command.getSurveyavailable())
                .recentImprovementsLast12Months(command.getRecentImprovementsLast12Months())
                .uploadInvoicesForImprovements(command.getUploadInvoicesForImprovements() != null ? command.getUploadInvoicesForImprovements().getFilePath() + "|" + command.getUploadInvoicesForImprovements().getFileName() : null)
                .summarizePropertyCondition(command.getSummarizePropertyCondition())
                .discloseKnownRepairsOrDefects(command.getDiscloseKnownRepairsOrDefects())
                .listItemsNotIncludedInSale(command.getListItemsNotIncludedInSale())

                .isThereAMortgage(command.getIsThereAMortgage())
                .lenderName(command.getLenderName())
                .loanNumber(command.getLoanNumber())
                .estimatedPayoffAmount(command.getEstimatedPayoffAmount())
                .uploadLatestMortgageStatement(command.getUploadLatestMortgageStatement() != null ? command.getUploadLatestMortgageStatement().getFilePath() + "|" + command.getUploadLatestMortgageStatement().getFileName() : null)
                .secondLienOrHeloc(command.getSecondLienOrHeloc())
                .irsLiensOrJudgments(command.getIrsLiensOrJudgments())
                .uploadTaxProrationAgreement(command.getUploadTaxProrationAgreement() != null ? command.getUploadTaxProrationAgreement().getFilePath() + "|" + command.getUploadTaxProrationAgreement().getFileName() : null)

                //Utilities
                .electricProvider(command.getElectricProvider())
                .electricProviderAccountNumber(command.getElectricProviderAccountNumber())
                .waterSewerProvider(command.getWaterSewerProvider())
                .gasProvider(command.getGasProvider())
                .gasProviderAccountNumber(command.getGasProviderAccountNumber())
                .trashServiceProvider(command.getTrashServiceProvider())
                .uploadLatestUtilityBill(command.getUploadLatestUtilityBill() != null ? command.getUploadLatestUtilityBill().getFilePath() + "|" + command.getUploadLatestUtilityBill().getFileName() : null)

                //Rental
                .uploadSellersDisclosureForm(command.getUploadSellersDisclosureForm() != null ? command.getUploadSellersDisclosureForm().getFilePath() + "|" + command.getUploadSellersDisclosureForm().getFileName() : null)
                .uploadTenantEstoppel(command.getUploadTenantEstoppel() != null ? command.getUploadTenantEstoppel().getFilePath() + "|" + command.getUploadTenantEstoppel().getFileName() : null)
                .uploadRentalAgreement(command.getUploadRentalAgreement() != null ? command.getUploadRentalAgreement().getFilePath() + "|" + command.getUploadRentalAgreement().getFileName() : null)

                //HOA
                .hoaApprovalProcessingTime(command.getHoaApprovalProcessingTime())
                .hoaDuesAmount(command.getHoaDuesAmount())

                //Legal & Estate
                .proofOfOwnershipDocument(command.getProofOfOwnershipDocument() != null ? command.getProofOfOwnershipDocument().getFilePath() + "|" + command.getProofOfOwnershipDocument().getFileName() : null)
                .powerOfAttorneyDocument(command.getPowerOfAttorneyDocument() != null ? command.getPowerOfAttorneyDocument().getFilePath() + "|" + command.getPowerOfAttorneyDocument().getFileName() : null)
                .trustOrEstateDocuments(command.getTrustOrEstateDocuments() != null ? command.getTrustOrEstateDocuments().getFilePath() + "|" + command.getTrustOrEstateDocuments().getFileName() : null)

                .build()
        );
    }

}
