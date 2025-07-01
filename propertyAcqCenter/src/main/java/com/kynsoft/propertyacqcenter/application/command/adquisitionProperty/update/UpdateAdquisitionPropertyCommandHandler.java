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

                .uploadGovernmentIssuedId(command.getUploadGovernmentIssuedId().getFilePath() + "|" + command.getUploadGovernmentIssuedId().getFileName())
                .hoaApplicationForm(command.getHoaApplicationForm().getFilePath() + "|" + command.getHoaApplicationForm().getFileName())
                .hoaApplicationUpload(command.getHoaApplicationUpload().getFilePath() + "|" + command.getHoaApplicationUpload().getFileName())
                .hoaFinancials(command.getHoaFinancials().getFilePath() + "|" + command.getHoaFinancials().getFileName())
                .hoaRulesRegulations(command.getHoaRulesRegulations().getFilePath() + "|" + command.getHoaRulesRegulations().getFileName())
                .buyerCarRegistration(command.getBuyerCarRegistration().getFilePath() + "|" + command.getBuyerCarRegistration().getFileName())
                .buyerBackgroundCheck(command.getBuyerBackgroundCheck().getFilePath() + "|" + command.getBuyerBackgroundCheck().getFileName())
                .commitmentLetter(command.getCommitmentLetter().getFilePath() + "|" + command.getCommitmentLetter().getFileName())
                .appraisalReport(command.getAppraisalReport().getFilePath() + "|" + command.getAppraisalReport().getFileName())
                .inspectionReport(command.getInspectionReport().getFilePath() + "|" + command.getInspectionReport().getFileName())
                .sellerDisclosureForm(command.getSellerDisclosureForm().getFilePath() + "|" + command.getSellerDisclosureForm().getFileName())
                .surveyDocument(command.getSurveyDocument().getFilePath() + "|" + command.getSurveyDocument().getFileName())
                .titleCommitment(command.getTitleCommitment().getFilePath() + "|" + command.getTitleCommitment().getFileName())
                .legalEntityCertificationStatus(command.getLegalEntityCertificationStatus().getFilePath() + "|" + command.getLegalEntityCertificationStatus().getFileName())
                .assignmentOfContract(command.getAssignmentOfContract().getFilePath() + "|" + command.getAssignmentOfContract().getFileName())
                .ownerExecutedContract(command.getOwnerExecutedContract().getFilePath() + "|" + command.getOwnerExecutedContract().getFileName())
                .contractAddendum(command.getContractAddendum().getFilePath() + "|" + command.getContractAddendum().getFileName())
                .finalSettlementStatement(command.getFinalSettlementStatement().getFilePath() + "|" + command.getFinalSettlementStatement().getFileName())
                .bankStatementRequest(command.getBankStatementRequest().getFilePath() + "|" + command.getBankStatementRequest().getFileName())
                .warrantyDeed(command.getWarrantyDeed().getFilePath() + "|" + command.getWarrantyDeed().getFileName())
                .titleInsurance(command.getTitleInsurance().getFilePath() + "|" + command.getTitleInsurance().getFileName())
                .executedClosingDocuments(command.getExecutedClosingDocuments().getFilePath() + "|" + command.getExecutedClosingDocuments().getFileName())

                .build()
        );
    }

}
