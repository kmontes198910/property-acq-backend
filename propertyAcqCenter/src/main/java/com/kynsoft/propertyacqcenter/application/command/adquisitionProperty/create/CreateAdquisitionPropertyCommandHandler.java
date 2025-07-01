package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.create;

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
public class CreateAdquisitionPropertyCommandHandler implements ICommandHandler<CreateAdquisitionPropertyCommand> {

    private final IAdquisitionPropertyService adquisitionPropertyService;
    private final ILegalEntityService legalEntityService;
    private final IPropertyService propertyService;
    private final ICompanyContactService companyContactService;

    public CreateAdquisitionPropertyCommandHandler(IAdquisitionPropertyService adquisitionPropertyService, 
                                                   ILegalEntityService legalEntityService,
                                                   IPropertyService propertyService,
                                                   ICompanyContactService companyContactService) {
        this.adquisitionPropertyService = adquisitionPropertyService;
        this.legalEntityService = legalEntityService;
        this.propertyService = propertyService;
        this.companyContactService = companyContactService;
    }

    @Override
    public void handle(CreateAdquisitionPropertyCommand command) {
        LegalEntityDto legalEntityDto = command.getBuyer() != null ? this.legalEntityService.findById(command.getBuyer()) : null;
        PropertyDto propertyDto = command.getProperty() != null ? this.propertyService.getById(command.getProperty()) : null;
        CompanyContactDto contactDto = command.getContact() != null ? this.companyContactService.findById(command.getContact()) : null;

        adquisitionPropertyService.create(AdquisitionPropertyDto
                .builder()
                .id(command.getId())
                .buyer(legalEntityDto)
                .property(propertyDto)
                .contact(contactDto)
                .buyerNameAndYearVehicle(command.getBuyerNameAndYearVehicle())
                .buyerLicenseTagNo(command.getBuyerLicenseTagNo())
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

                .build()
        );
    }

}
