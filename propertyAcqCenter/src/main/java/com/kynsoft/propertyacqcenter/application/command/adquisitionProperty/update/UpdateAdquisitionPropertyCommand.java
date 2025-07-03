package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.create.CreateDocumentRequest;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateAdquisitionPropertyCommand implements ICommand {

    private UUID id;
    private UUID buyer;
    private String property;
    private UUID contact;

    private String buyerNameAndYearVehicle;
    private String buyerLicenseTagNo;

    private LocalDate dateAndTimeForInspections;
    private String instructionsForAccess;
    private LocalDate hoaBuyerInterviewDate;
    private LocalDate preferredMoveinDate;
    private String eSignAuthorization;
    private LocalDate finalWalkthroughDate;
    private String wireAccountHolderName;
    private String wireAccountNumber;
    private String wireRoutingNumber;
    private String zelleEmailorPhone;
    private String electricProviderConfirmation;
    private String gasServiceConfirmation;
    private String trashServiceConfirmation;
    private String waterSewerSetupConfirmation;
    private CreateDocumentRequest uploadGovernmentIssuedId;
    private CreateDocumentRequest hoaApplicationForm;
    private CreateDocumentRequest hoaApplicationUpload;
    private CreateDocumentRequest hoaFinancials;
    private CreateDocumentRequest hoaRulesRegulations;
    private CreateDocumentRequest buyerCarRegistration;
    private CreateDocumentRequest buyerBackgroundCheck;
    private CreateDocumentRequest commitmentLetter;
    private CreateDocumentRequest appraisalReport;
    private CreateDocumentRequest inspectionReport;
    private CreateDocumentRequest sellerDisclosureForm;
    private CreateDocumentRequest surveyDocument;
    private CreateDocumentRequest titleCommitment;
    private CreateDocumentRequest legalEntityCertificationStatus;
    private CreateDocumentRequest assignmentOfContract;
    private CreateDocumentRequest ownerExecutedContract;
    private CreateDocumentRequest contractAddendum;
    private CreateDocumentRequest finalSettlementStatement;
    private CreateDocumentRequest bankStatementRequest;
    private CreateDocumentRequest warrantyDeed;
    private CreateDocumentRequest titleInsurance;
    private CreateDocumentRequest executedClosingDocuments;

    private String buyerFullLegalName;
    private String buyerContactEmail;
    private String buyerEntityName;
    private String buyerMailingAddress;
    private String buyerMobilePhoneNumber;
    private String hoa4050certificationStatus;
    private String hoaValidatorContactName;
    private String hoaValidatorEmail;
    private String hoaValidatorPhoneNumber;
    private String closingCountdownClock;
    private LocalDate contractClosingDate;

    //Seller
    private String sellerFullName;//Sellers, Wholesaler, Real Estate Agent, Title Co.
    private String sellerEntityName;//Sellers, Wholesaler, Real Estate Agent
    private CreateDocumentRequest sellerArticlesOfIncorporation;//Sellers, Wholesaler, Real Estate Agent, Title Co.
    private CreateDocumentRequest sellerCertificateOfGoodStanding;//Sellers, Wholesaler, Real Estate Agent, Title Co.
    private CreateDocumentRequest sellerOperatingAgreement;//Sellers, Wholesaler, Real Estate Agent, Title Co.
    private String sellerOwnershipType;//Sellers, Wholesaler, Real Estate Agent, Title Co.
    private CreateDocumentRequest sellerResolutionToSell;

    // Información personal
    private String sellerSocialSecurityNumber;
    private String sellerMaritalStatus;
    private CreateDocumentRequest sellerGovernmentId;
    private CreateDocumentRequest sellerW9Form;

    // Información FIRPTA
    private Boolean sellerForeignSeller;
    private CreateDocumentRequest sellerFirptaAffidavit;

    // Información bancaria
    private String sellerWireAccountHolder;
    private String sellerWireAccountNumber;
    private String sellerWireRoutingNumber;
    private String zelleContact;

    //Title Company
    private LocalDate titleCompanyRequestForEstoppelLetter;
    private CreateDocumentRequest titleCompanyEarnestMoneyDepositConfirmation;

    public UpdateAdquisitionPropertyCommand(UUID id, UUID buyer, String property, UUID contact, String buyerNameAndYearVehicle,
                                            String buyerLicenseTagNo,
                                            LocalDate dateAndTimeForInspections, String instructionsForAccess, LocalDate hoaBuyerInterviewDate,
                                            LocalDate preferredMoveinDate, String eSignAuthorization, LocalDate finalWalkthroughDate,
                                            String wireAccountHolderName, String wireAccountNumber, String wireRoutingNumber,
                                            String zelleEmailorPhone, String electricProviderConfirmation, String gasServiceConfirmation,
                                            String trashServiceConfirmation, String waterSewerSetupConfirmation,
                                            CreateDocumentRequest uploadGovernmentIssuedId, CreateDocumentRequest hoaApplicationForm,
                                            CreateDocumentRequest hoaApplicationUpload, CreateDocumentRequest hoaFinancials, 
                                            CreateDocumentRequest hoaRulesRegulations, CreateDocumentRequest buyerCarRegistration,
                                            CreateDocumentRequest buyerBackgroundCheck, CreateDocumentRequest commitmentLetter,
                                            CreateDocumentRequest appraisalReport, CreateDocumentRequest inspectionReport,
                                            CreateDocumentRequest sellerDisclosureForm, CreateDocumentRequest surveyDocument,
                                            CreateDocumentRequest titleCommitment, CreateDocumentRequest legalEntityCertificationStatus,
                                            CreateDocumentRequest assignmentOfContract, CreateDocumentRequest ownerExecutedContract,
                                            CreateDocumentRequest contractAddendum, CreateDocumentRequest finalSettlementStatement,
                                            CreateDocumentRequest bankStatementRequest, CreateDocumentRequest warrantyDeed, CreateDocumentRequest titleInsurance,
                                            CreateDocumentRequest executedClosingDocuments, String buyerFullLegalName, String buyerContactEmail,
                                            String buyerEntityName, String buyerMailingAddress, String buyerMobilePhoneNumber, String hoa4050certificationStatus,
                                            String hoaValidatorContactName, String hoaValidatorEmail, String hoaValidatorPhoneNumber,
                                            String closingCountdownClock, LocalDate contractClosingDate,
                                            String sellerFullName, String sellerEntityName, CreateDocumentRequest sellerArticlesOfIncorporation,
                                            CreateDocumentRequest sellerCertificateOfGoodStanding, CreateDocumentRequest sellerOperatingAgreement,
                                            String sellerOwnershipType, CreateDocumentRequest sellerResolutionToSell, String sellerSocialSecurityNumber,
                                            String sellerMaritalStatus, CreateDocumentRequest sellerGovernmentId, CreateDocumentRequest sellerW9Form,
                                            Boolean sellerForeignSeller, CreateDocumentRequest sellerFirptaAffidavit, String sellerWireAccountHolder,
                                            String sellerWireAccountNumber, String sellerWireRoutingNumber, String zelleContact, LocalDate titleCompanyRequestForEstoppelLetter,
                                            CreateDocumentRequest titleCompanyEarnestMoneyDepositConfirmation) {
        this.id = id;
        this.buyer = buyer;
        this.property = property;
        this.contact = contact;
        this.buyerLicenseTagNo = buyerLicenseTagNo;
        this.buyerNameAndYearVehicle = buyerNameAndYearVehicle;
        this.dateAndTimeForInspections = dateAndTimeForInspections;//
        this.instructionsForAccess = instructionsForAccess;//
        this.hoaBuyerInterviewDate = hoaBuyerInterviewDate;//
        this.preferredMoveinDate = preferredMoveinDate;//
        this.eSignAuthorization = eSignAuthorization;//
        this.finalWalkthroughDate = finalWalkthroughDate;//
        this.wireAccountHolderName = wireAccountHolderName;//
        this.wireAccountNumber = wireAccountNumber;//
        this.wireRoutingNumber = wireRoutingNumber;//
        this.zelleEmailorPhone = zelleEmailorPhone;//
        this.electricProviderConfirmation = electricProviderConfirmation;//
        this.gasServiceConfirmation = gasServiceConfirmation;//
        this.trashServiceConfirmation = trashServiceConfirmation;//
        this.waterSewerSetupConfirmation = waterSewerSetupConfirmation;//
        this.uploadGovernmentIssuedId = uploadGovernmentIssuedId;
        this.hoaApplicationForm = hoaApplicationForm;
        this.hoaApplicationUpload = hoaApplicationUpload;
        this.hoaFinancials = hoaFinancials;
        this.hoaRulesRegulations = hoaRulesRegulations;
        this.buyerCarRegistration = buyerCarRegistration;
        this.buyerBackgroundCheck = buyerBackgroundCheck;
        this.commitmentLetter = commitmentLetter;
        this.appraisalReport = appraisalReport;
        this.inspectionReport = inspectionReport;
        this.sellerDisclosureForm = sellerDisclosureForm;
        this.surveyDocument = surveyDocument;
        this.titleCommitment = titleCommitment;
        this.legalEntityCertificationStatus = legalEntityCertificationStatus;
        this.assignmentOfContract = assignmentOfContract;
        this.ownerExecutedContract = ownerExecutedContract;
        this.contractAddendum = contractAddendum;
        this.finalSettlementStatement = finalSettlementStatement;
        this.bankStatementRequest = bankStatementRequest;
        this.warrantyDeed = warrantyDeed;
        this.titleInsurance = titleInsurance;
        this.executedClosingDocuments = executedClosingDocuments;
        this.buyerFullLegalName = buyerFullLegalName;
        this.buyerContactEmail = buyerContactEmail;
        this.buyerEntityName = buyerEntityName;
        this.buyerMailingAddress = buyerMailingAddress;
        this.buyerMobilePhoneNumber = buyerMobilePhoneNumber;
        this.hoa4050certificationStatus = hoa4050certificationStatus;
        this.hoaValidatorContactName = hoaValidatorContactName;
        this.hoaValidatorEmail = hoaValidatorEmail;
        this.hoaValidatorPhoneNumber = hoaValidatorPhoneNumber;
        this.closingCountdownClock = closingCountdownClock;
        this.contractClosingDate = contractClosingDate;
        this.sellerFullName = sellerFullName;
        this.sellerEntityName = sellerEntityName;
        this.sellerArticlesOfIncorporation = sellerArticlesOfIncorporation;
        this.sellerCertificateOfGoodStanding = sellerCertificateOfGoodStanding;
        this.sellerOperatingAgreement = sellerOperatingAgreement;
        this.sellerOwnershipType = sellerOwnershipType;
        this.sellerResolutionToSell = sellerResolutionToSell;
        this.sellerSocialSecurityNumber = sellerSocialSecurityNumber;
        this.sellerMaritalStatus = sellerMaritalStatus;
        this.sellerGovernmentId = sellerGovernmentId;
        this.sellerW9Form = sellerW9Form;
        this.sellerForeignSeller = sellerForeignSeller;
        this.sellerFirptaAffidavit = sellerFirptaAffidavit;
        this.sellerWireAccountHolder = sellerWireAccountHolder;
        this.sellerWireAccountNumber = sellerWireAccountNumber;
        this.sellerWireRoutingNumber = sellerWireRoutingNumber;
        this.zelleContact = zelleContact;
        this.titleCompanyRequestForEstoppelLetter = titleCompanyRequestForEstoppelLetter;
        this.titleCompanyEarnestMoneyDepositConfirmation = titleCompanyEarnestMoneyDepositConfirmation;
    }

    public static UpdateAdquisitionPropertyCommand fromRequest(UpdateAdquisitionPropertyRequest request, UUID id) {
        return new UpdateAdquisitionPropertyCommand(
                id,
                request.getBuyer(),
                request.getProperty(),
                request.getContact(),
                request.getBuyerNameAndYearVehicle(),
                request.getBuyerLicenseTagNo(),
                request.getDateAndTimeForInspections(),
                request.getInstructionsForAccess(),
                request.getHoaBuyerInterviewDate(),
                request.getPreferredMoveinDate(),
                request.getESignAuthorization(),
                request.getFinalWalkthroughDate(),
                request.getWireAccountHolderName(),
                request.getWireAccountNumber(),
                request.getWireRoutingNumber(),
                request.getZelleEmailorPhone(),
                request.getElectricProviderConfirmation(),
                request.getGasServiceConfirmation(),
                request.getTrashServiceConfirmation(),
                request.getWaterSewerSetupConfirmation(),
                request.getUploadGovernmentIssuedId(),
                request.getHoaApplicationForm(),
                request.getHoaApplicationUpload(),
                request.getHoaFinancials(),
                request.getHoaRulesRegulations(),
                request.getBuyerCarRegistration(),
                request.getBuyerBackgroundCheck(),
                request.getCommitmentLetter(),
                request.getAppraisalReport(),
                request.getInspectionReport(),
                request.getSellerDisclosureForm(),
                request.getSurveyDocument(),
                request.getTitleCommitment(),
                request.getLegalEntityCertificationStatus(),
                request.getAssignmentOfContract(),
                request.getOwnerExecutedContract(),
                request.getContractAddendum(),
                request.getFinalSettlementStatement(),
                request.getBankStatementRequest(),
                request.getWarrantyDeed(),
                request.getTitleInsurance(),
                request.getExecutedClosingDocuments(),
                request.getBuyerFullLegalName(),
                request.getBuyerContactEmail(),
                request.getBuyerEntityName(),
                request.getBuyerMailingAddress(),
                request.getBuyerMobilePhoneNumber(),
                request.getHoa4050certificationStatus(),
                request.getHoaValidatorContactName(),
                request.getHoaValidatorEmail(),
                request.getHoaValidatorPhoneNumber(),
                request.getClosingCountdownClock(),
                request.getContractClosingDate(),
                request.getSellerFullName(),
                request.getSellerEntityName(),
                request.getSellerArticlesOfIncorporation(),
                request.getSellerCertificateOfGoodStanding(),
                request.getSellerOperatingAgreement(),
                request.getSellerOwnershipType(),
                request.getSellerResolutionToSell(),
                request.getSellerSocialSecurityNumber(),
                request.getSellerMaritalStatus(),
                request.getSellerGovernmentId(),
                request.getSellerW9Form(),
                request.getSellerForeignSeller(),
                request.getSellerFirptaAffidavit(),
                request.getSellerWireAccountHolder(),
                request.getSellerWireAccountNumber(),
                request.getSellerWireRoutingNumber(),
                request.getZelleContact(),
                request.getTitleCompanyRequestForEstoppelLetter(),
                request.getTitleCompanyEarnestMoneyDepositConfirmation()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateAdquisitionPropertyMessage(id);
    }
}
