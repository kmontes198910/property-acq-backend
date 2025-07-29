package com.kynsoft.propertyacqcenter.infrastructure.services;

import com.kynsof.share.core.domain.request.FilterCriteria;
import com.kynsof.share.core.domain.response.PaginatedResponse;
import com.kynsof.share.core.infrastructure.specifications.GenericSpecificationsBuilder;
import com.kynsoft.propertyacqcenter.application.response.AdquisitionPropertyResponse;
import com.kynsoft.propertyacqcenter.domain.dto.AdquisitionPropertyDocumentDto;
import com.kynsoft.propertyacqcenter.domain.dto.AdquisitionPropertyDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.AddressNotFoundException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.NotDeleteException;
import com.kynsoft.propertyacqcenter.domain.dto.exception.PurchaseForPropertyNotFoundException;
import com.kynsoft.propertyacqcenter.domain.services.IAdquisitionPropertyService;
import com.kynsoft.propertyacqcenter.infrastructure.entity.AdquisitionProperty;
import com.kynsoft.propertyacqcenter.infrastructure.entity.CompanyContact;
import com.kynsoft.propertyacqcenter.infrastructure.entity.LegalEntity;
import com.kynsoft.propertyacqcenter.infrastructure.entity.Property;
import com.kynsoft.propertyacqcenter.infrastructure.entity.embedded.adquisitionProperty.AdquisitionPropertyBuyer;
import com.kynsoft.propertyacqcenter.infrastructure.entity.embedded.adquisitionProperty.AdquisitionPropertyBuyerPersonalBankInfo;
import com.kynsoft.propertyacqcenter.infrastructure.entity.embedded.adquisitionProperty.AdquisitionPropertyBuyerUtilitiesInfo;
import com.kynsoft.propertyacqcenter.infrastructure.entity.embedded.adquisitionProperty.AdquisitionPropertyHoa;
import com.kynsoft.propertyacqcenter.infrastructure.repository.command.AdquisitionPropertyWriteDataJPARepository;
import com.kynsoft.propertyacqcenter.infrastructure.repository.query.AdquisitionPropertyReadDataJPARepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;

@Service
@Primary
public class AdquisitionPropertyServiceImpl implements IAdquisitionPropertyService {

    private final AdquisitionPropertyReadDataJPARepository repositoryQuery;
    private final AdquisitionPropertyWriteDataJPARepository repositoryCommand;

    public AdquisitionPropertyServiceImpl(AdquisitionPropertyReadDataJPARepository repositoryQuery,
            AdquisitionPropertyWriteDataJPARepository repositoryCommand) {
        this.repositoryQuery = repositoryQuery;
        this.repositoryCommand = repositoryCommand;
    }

    @Override
    @Transactional
    public UUID create(AdquisitionPropertyDto object) {
        return repositoryCommand.save(new AdquisitionProperty(object)).getId();
    }

    @Override
    @Transactional
    public void update(AdquisitionPropertyDto object) {
        AdquisitionProperty update = this.findByIdSimple(object.getId());
        update.setAdquisitionPropertyHoa(createAdquisitionPropertyHoa(object, update));
        update.setAdquisitionPropertyBuyer(createAdquisitionPropertyBuyer(object, update));
        update.setAdquisitionPropertyBuyerPersonalBankInfo(createAdquisitionPropertyBuyerPersonalBankInfo(object, update));
        update.setAdquisitionPropertyBuyerUtilitiesInfo(createAdquisitionPropertyBuyerUtilitiesInfo(object, update));

        update.setBuyer(object.getBuyer() != null ? new LegalEntity(object.getBuyer()) : null);
        update.setContact(object.getContact() != null ? new CompanyContact(object.getContact()) : null);
        update.setProperty(new Property(object.getProperty()));

        update.setBuyerNameAndYearVehicle(object.getBuyerNameAndYearVehicle() != null ? object.getBuyerNameAndYearVehicle() : update.getBuyerNameAndYearVehicle());
        update.setBuyerLicenseTagNo(object.getBuyerLicenseTagNo() != null ? object.getBuyerLicenseTagNo() : update.getBuyerLicenseTagNo());

        update.setDateAndTimeForInspections(object.getDateAndTimeForInspections() != null ? object.getDateAndTimeForInspections() : update.getDateAndTimeForInspections());
        update.setInstructionsForAccess(object.getInstructionsForAccess() != null ? object.getInstructionsForAccess() : update.getInstructionsForAccess());
        update.setHoaBuyerInterviewDate(object.getHoaBuyerInterviewDate() != null ? object.getHoaBuyerInterviewDate() : update.getHoaBuyerInterviewDate());
        update.setPreferredMoveinDate(object.getPreferredMoveinDate() != null ? object.getPreferredMoveinDate() : update.getPreferredMoveinDate());
        update.setESignAuthorization(object.getESignAuthorization() != null ? object.getESignAuthorization() : update.getESignAuthorization());
        update.setFinalWalkthroughDate(object.getFinalWalkthroughDate() != null ? object.getFinalWalkthroughDate() : update.getFinalWalkthroughDate());
        update.setWireAccountHolderName(object.getWireAccountHolderName() != null ? object.getWireAccountHolderName() : update.getWireAccountHolderName());
        update.setWireAccountNumber(object.getWireAccountNumber() != null ? object.getWireAccountNumber() : update.getWireAccountNumber());
        update.setWireRoutingNumber(object.getWireRoutingNumber() != null ? object.getWireRoutingNumber() : update.getWireRoutingNumber());
        update.setZelleEmailorPhone(object.getZelleEmailorPhone() != null ? object.getZelleEmailorPhone() : update.getZelleEmailorPhone());
        update.setElectricProviderConfirmation(object.getElectricProviderConfirmation() != null ? object.getElectricProviderConfirmation() : update.getElectricProviderConfirmation());
        update.setGasServiceConfirmation(object.getGasServiceConfirmation() != null ? object.getGasServiceConfirmation() : update.getGasServiceConfirmation());
        update.setTrashServiceConfirmation(object.getTrashServiceConfirmation() != null ? object.getTrashServiceConfirmation() : update.getTrashServiceConfirmation());
        update.setWaterSewerSetupConfirmation(object.getWaterSewerSetupConfirmation() != null ? object.getWaterSewerSetupConfirmation() : update.getWaterSewerSetupConfirmation());

        update.setUploadGovernmentIssuedId(object.getUploadGovernmentIssuedId() != null ? object.getUploadGovernmentIssuedId() : update.getUploadGovernmentIssuedId());
        update.setHoaApplicationForm(object.getHoaApplicationForm() != null ? object.getHoaApplicationForm() : update.getHoaApplicationForm());
        update.setHoaApplicationUpload(object.getHoaApplicationUpload() != null ? object.getHoaApplicationUpload() : update.getHoaApplicationUpload());
        update.setHoaFinancials(object.getHoaFinancials() != null ? object.getHoaFinancials() : update.getHoaFinancials());
        update.setHoaRulesRegulations(object.getHoaRulesRegulations() != null ? object.getHoaRulesRegulations() : update.getHoaRulesRegulations());
        update.setBuyerCarRegistration(object.getBuyerCarRegistration() != null ? object.getBuyerCarRegistration() : update.getBuyerCarRegistration());
        update.setBuyerBackgroundCheck(object.getBuyerBackgroundCheck() != null ? object.getBuyerBackgroundCheck() : update.getBuyerBackgroundCheck());
        update.setCommitmentLetter(object.getCommitmentLetter() != null ? object.getCommitmentLetter() : update.getCommitmentLetter());
        update.setAppraisalReport(object.getAppraisalReport() != null ? object.getAppraisalReport() : update.getAppraisalReport());
        update.setInspectionReport(object.getInspectionReport() != null ? object.getInspectionReport() : update.getInspectionReport());
        update.setSellerDisclosureForm(object.getSellerDisclosureForm() != null ? object.getSellerDisclosureForm() : update.getSellerDisclosureForm());
        update.setSurveyDocument(object.getSurveyDocument() != null ? object.getSurveyDocument() : update.getSurveyDocument());
        update.setTitleCommitment(object.getTitleCommitment() != null ? object.getTitleCommitment() : update.getTitleCommitment());
        update.setLegalEntityCertificationStatus(object.getLegalEntityCertificationStatus() != null ? object.getLegalEntityCertificationStatus() : update.getLegalEntityCertificationStatus());
        update.setAssignmentOfContract(object.getAssignmentOfContract() != null ? object.getAssignmentOfContract() : update.getAssignmentOfContract());
        update.setOwnerExecutedContract(object.getOwnerExecutedContract() != null ? object.getOwnerExecutedContract() : update.getOwnerExecutedContract());
        update.setContractAddendum(object.getContractAddendum() != null ? object.getContractAddendum() : update.getContractAddendum());
        update.setFinalSettlementStatement(object.getFinalSettlementStatement() != null ? object.getFinalSettlementStatement() : update.getFinalSettlementStatement());
        //update.setBankStatementRequest(object.getBankStatementRequest() != null ? object.getBankStatementRequest() : update.getBankStatementRequest());
        update.setWarrantyDeed(object.getWarrantyDeed() != null ? object.getWarrantyDeed() : update.getWarrantyDeed());
        update.setTitleInsurance(object.getTitleInsurance() != null ? object.getTitleInsurance() : update.getTitleInsurance());
        update.setExecutedClosingDocuments(object.getExecutedClosingDocuments() != null ? object.getExecutedClosingDocuments() : update.getExecutedClosingDocuments());

        update.setBuyerFullLegalName(object.getBuyerFullLegalName() != null ? object.getBuyerFullLegalName() : update.getBuyerFullLegalName());
        update.setBuyerContactEmail(object.getBuyerContactEmail() != null ? object.getBuyerContactEmail() : update.getBuyerContactEmail());
        update.setBuyerEntityName(object.getBuyerEntityName() != null ? object.getBuyerEntityName() : update.getBuyerEntityName());
        update.setBuyerMailingAddress(object.getBuyerMailingAddress() != null ? object.getBuyerMailingAddress() : update.getBuyerMailingAddress());
        update.setBuyerMobilePhoneNumber(object.getBuyerMobilePhoneNumber() != null ? object.getBuyerMobilePhoneNumber() : update.getBuyerMobilePhoneNumber());
        update.setHoa4050certificationStatus(object.getHoa4050certificationStatus() != null ? object.getHoa4050certificationStatus() : update.getHoa4050certificationStatus());
        update.setHoaValidatorContactName(object.getHoaValidatorContactName() != null ? object.getHoaValidatorContactName() : update.getHoaValidatorContactName());
        update.setHoaValidatorEmail(object.getHoaValidatorEmail() != null ? object.getHoaValidatorEmail() : update.getHoaValidatorEmail());
        update.setHoaValidatorPhoneNumber(object.getHoaValidatorPhoneNumber() != null ? object.getHoaValidatorPhoneNumber() : update.getHoaValidatorPhoneNumber());
        update.setContractClosingDate(object.getContractClosingDate() != null ? object.getContractClosingDate() : update.getContractClosingDate());

        update.setSellerFullName(object.getSellerFullName() != null ? object.getSellerFullName() : update.getSellerFullName());
        update.setSellerEntityName(object.getSellerEntityName() != null ? object.getSellerEntityName() : update.getSellerEntityName());
        update.setSellerArticlesOfIncorporation(object.getSellerArticlesOfIncorporation() != null ? object.getSellerArticlesOfIncorporation() : update.getSellerArticlesOfIncorporation());
        update.setSellerCertificateOfGoodStanding(object.getSellerCertificateOfGoodStanding() != null ? object.getSellerCertificateOfGoodStanding() : update.getSellerCertificateOfGoodStanding());
        update.setSellerOperatingAgreement(object.getSellerOperatingAgreement() != null ? object.getSellerOperatingAgreement() : update.getSellerOperatingAgreement());
        update.setSellerOwnershipType(object.getSellerOwnershipType() != null ? object.getSellerOwnershipType() : update.getSellerOwnershipType());
        update.setSellerResolutionToSell(object.getSellerResolutionToSell() != null ? object.getSellerResolutionToSell() : update.getSellerResolutionToSell());
        update.setSellerSocialSecurityNumber(object.getSellerSocialSecurityNumber() != null ? object.getSellerSocialSecurityNumber() : update.getSellerSocialSecurityNumber());
        update.setSellerMaritalStatus(object.getSellerMaritalStatus() != null ? object.getSellerMaritalStatus() : update.getSellerMaritalStatus());
        update.setSellerGovernmentId(object.getSellerGovernmentId() != null ? object.getSellerGovernmentId() : update.getSellerGovernmentId());
        update.setSellerW9Form(object.getSellerW9Form() != null ? object.getSellerW9Form() : update.getSellerW9Form());
        update.setSellerForeignSeller(object.getSellerForeignSeller() != null ? object.getSellerForeignSeller() : update.getSellerForeignSeller());
        update.setSellerFirptaAffidavit(object.getSellerFirptaAffidavit() != null ? object.getSellerFirptaAffidavit() : update.getSellerFirptaAffidavit());
        update.setSellerWireAccountHolder(object.getSellerWireAccountHolder() != null ? object.getSellerWireAccountHolder() : update.getSellerWireAccountHolder());
        update.setSellerWireAccountNumber(object.getSellerWireAccountNumber() != null ? object.getSellerWireAccountNumber() : update.getSellerWireAccountNumber());
        update.setSellerWireRoutingNumber(object.getSellerWireRoutingNumber() != null ? object.getSellerWireRoutingNumber() : update.getSellerWireRoutingNumber());
        update.setZelleContact(object.getZelleContact() != null ? object.getZelleContact() : update.getZelleContact());
        update.setTitleCompanyRequestForEstoppelLetter(object.getTitleCompanyRequestForEstoppelLetter() != null ? object.getTitleCompanyRequestForEstoppelLetter() : update.getTitleCompanyRequestForEstoppelLetter());
        update.setTitleCompanyEarnestMoneyDepositConfirmation(object.getTitleCompanyEarnestMoneyDepositConfirmation() != null ? object.getTitleCompanyEarnestMoneyDepositConfirmation() : update.getTitleCompanyEarnestMoneyDepositConfirmation());

        update.setSurveyavailable(object.getSurveyavailable() != null ? object.getSurveyavailable() : update.getSurveyavailable());
        update.setRecentImprovementsLast12Months(object.getRecentImprovementsLast12Months() != null ? object.getRecentImprovementsLast12Months() : update.getRecentImprovementsLast12Months());
        update.setUploadInvoicesForImprovements(object.getUploadInvoicesForImprovements() != null ? object.getUploadInvoicesForImprovements() : update.getUploadInvoicesForImprovements());
        update.setSummarizePropertyCondition(object.getSummarizePropertyCondition() != null ? object.getSummarizePropertyCondition() : update.getSummarizePropertyCondition());
        update.setDiscloseKnownRepairsOrDefects(object.getDiscloseKnownRepairsOrDefects() != null ? object.getDiscloseKnownRepairsOrDefects() : update.getDiscloseKnownRepairsOrDefects());
        update.setListItemsNotIncludedInSale(object.getListItemsNotIncludedInSale() != null ? object.getListItemsNotIncludedInSale() : update.getListItemsNotIncludedInSale());

        update.setIsThereAMortgage(object.getIsThereAMortgage() != null ? object.getIsThereAMortgage() : update.getIsThereAMortgage());
        update.setLenderName(object.getLenderName() != null ? object.getLenderName() : update.getLenderName());
        update.setLoanNumber(object.getLoanNumber() != null ? object.getLoanNumber() : update.getLoanNumber());
        update.setEstimatedPayoffAmount(object.getEstimatedPayoffAmount() != null ? object.getEstimatedPayoffAmount() : update.getEstimatedPayoffAmount());
        update.setUploadLatestMortgageStatement(object.getUploadLatestMortgageStatement() != null ? object.getUploadLatestMortgageStatement() : update.getUploadLatestMortgageStatement());
        update.setSecondLienOrHeloc(object.getSecondLienOrHeloc() != null ? object.getSecondLienOrHeloc() : update.getSecondLienOrHeloc());
        update.setIrsLiensOrJudgments(object.getIrsLiensOrJudgments() != null ? object.getIrsLiensOrJudgments() : update.getIrsLiensOrJudgments());
        update.setUploadTaxProrationAgreement(object.getUploadTaxProrationAgreement() != null ? object.getUploadTaxProrationAgreement() : update.getUploadTaxProrationAgreement());
        update.setLienSearch(object.getLienSearch() != null ? object.getLienSearch() : update.getLienSearch());
        update.setFinalAgreedSalesPrice(object.getFinalAgreedSalesPrice() != null ? object.getFinalAgreedSalesPrice() : update.getFinalAgreedSalesPrice());

        update.setElectricProvider(object.getElectricProvider() != null ? object.getElectricProvider() : update.getElectricProvider());
        update.setElectricProviderAccountNumber(object.getElectricProviderAccountNumber() != null ? object.getElectricProviderAccountNumber() : update.getElectricProviderAccountNumber());
        update.setWaterSewerProvider(object.getWaterSewerProvider() != null ? object.getWaterSewerProvider() : update.getWaterSewerProvider());
        update.setGasProvider(object.getGasProvider() != null ? object.getGasProvider() : update.getGasProvider());
        update.setGasProviderAccountNumber(object.getGasProviderAccountNumber() != null ? object.getGasProviderAccountNumber() : update.getGasProviderAccountNumber());
        update.setTrashServiceProvider(object.getTrashServiceProvider() != null ? object.getTrashServiceProvider() : update.getTrashServiceProvider());
        update.setUploadLatestUtilityBill(object.getUploadLatestUtilityBill() != null ? object.getUploadLatestUtilityBill() : update.getUploadLatestUtilityBill());

        update.setUploadSellersDisclosureForm(object.getUploadSellersDisclosureForm() != null ? object.getUploadSellersDisclosureForm() : update.getUploadSellersDisclosureForm());
        update.setUploadTenantEstoppel(object.getUploadTenantEstoppel() != null ? object.getUploadTenantEstoppel() : update.getUploadTenantEstoppel());
        update.setUploadRentalAgreement(object.getUploadRentalAgreement() != null ? object.getUploadRentalAgreement() : update.getUploadRentalAgreement());

        update.setHoaApprovalProcessingTime(object.getHoaApprovalProcessingTime() != null ? object.getHoaApprovalProcessingTime() : update.getHoaApprovalProcessingTime());
        update.setHoaDuesAmount(object.getHoaDuesAmount() != null ? object.getHoaDuesAmount() : update.getHoaDuesAmount());

        update.setProofOfOwnershipDocument(object.getProofOfOwnershipDocument() != null ? object.getProofOfOwnershipDocument() : update.getProofOfOwnershipDocument());
        update.setPowerOfAttorneyDocument(object.getPowerOfAttorneyDocument() != null ? object.getPowerOfAttorneyDocument() : update.getPowerOfAttorneyDocument());
        update.setTrustOrEstateDocuments(object.getTrustOrEstateDocuments() != null ? object.getTrustOrEstateDocuments() : update.getTrustOrEstateDocuments());

        update.setEnterWireInstructions(object.getEnterWireInstructions() != null ? object.getEnterWireInstructions() : update.getEnterWireInstructions());
        update.setAuthorizeTitleCompanyInfo(object.getAuthorizeTitleCompanyInfo() != null ? object.getAuthorizeTitleCompanyInfo() : update.getAuthorizeTitleCompanyInfo());

        update.setPropertyAccessCode(object.getPropertyAccessCode() != null ? object.getPropertyAccessCode() : update.getPropertyAccessCode());
        update.setTimeForAccess(object.getTimeForAccess() != null ? object.getTimeForAccess() : update.getTimeForAccess());
        update.setInstructionsForInspections(object.getInstructionsForInspections() != null ? object.getInstructionsForInspections() : update.getInstructionsForInspections());

        update.setHoaQASheet(object.getHoaQASheet() != null ? object.getHoaQASheet() : update.getHoaQASheet());
        update.setHoaEstoppelCertificate(object.getHoaEstoppelCertificate() != null ? object.getHoaEstoppelCertificate() : update.getHoaEstoppelCertificate());
        update.setHoaApprovalLetter(object.getHoaApprovalLetter() != null ? object.getHoaApprovalLetter() : update.getHoaApprovalLetter());
        update.setBuildingYearBuilt(object.getBuildingYearBuilt() != null ? object.getBuildingYearBuilt() : update.getBuildingYearBuilt());
        update.setHoaMoveInFee(object.getHoaMoveInFee() != null ? object.getHoaMoveInFee() : update.getHoaMoveInFee());
        update.setHoaInterviewRequired(object.getHoaInterviewRequired() != null ? object.getHoaInterviewRequired() : update.getHoaInterviewRequired());
        update.setHoaApplicationInstructions(object.getHoaApplicationInstructions() != null ? object.getHoaApplicationInstructions() : update.getHoaApplicationInstructions());
        update.setApplicationFeesAmount(object.getApplicationFeesAmount() != null ? object.getApplicationFeesAmount() : update.getApplicationFeesAmount());
        update.setApplicationFeesSentDate(object.getApplicationFeesSentDate() != null ? object.getApplicationFeesSentDate() : update.getApplicationFeesSentDate());
        update.setRentalRestrictions(object.getRentalRestrictions() != null ? object.getRentalRestrictions() : update.getRentalRestrictions());
        update.setHoaSpecialAssessmentAmount(object.getHoaSpecialAssessmentAmount() != null ? object.getHoaSpecialAssessmentAmount() : update.getHoaSpecialAssessmentAmount());
        update.setHoaHaveReserve(object.getHoaHaveReserve() != null ? object.getHoaHaveReserve() : update.getHoaHaveReserve());
        update.setHoaCOICertificate(object.getHoaCOICertificate() != null ? object.getHoaCOICertificate() : update.getHoaCOICertificate());
        update.setBuyersSocialSecurity(object.getBuyersSocialSecurity() != null ? object.getBuyersSocialSecurity() : update.getBuyersSocialSecurity());
        update.setHoaW9TaxID(object.getHoaW9TaxID() != null ? object.getHoaW9TaxID() : update.getHoaW9TaxID());

        update.setWsalerClosingDate(object.getWsalerClosingDate() != null ? object.getWsalerClosingDate() : update.getWsalerClosingDate());
        update.setWsalerAssignmentOfContract(object.getWsalerAssignmentOfContract() != null ? object.getWsalerAssignmentOfContract() : update.getWsalerAssignmentOfContract());

        update.setRequestAppraisalDate(object.getRequestAppraisalDate() != null ? object.getRequestAppraisalDate() : update.getRequestAppraisalDate());
        update.setConfirmedAppointmentDateTime(object.getConfirmedAppointmentDateTime() != null ? object.getConfirmedAppointmentDateTime() : update.getConfirmedAppointmentDateTime());
        update.setAppraisedValue(object.getAppraisedValue() != null ? object.getAppraisedValue() : update.getAppraisedValue());
        update.setAppraiserLicenseNumber(object.getAppraiserLicenseNumber() != null ? object.getAppraiserLicenseNumber() : update.getAppraiserLicenseNumber());
        update.setAsIsValue(object.getAsIsValue() != null ? object.getAsIsValue() : update.getAsIsValue());
        update.setAsRepairedValue(object.getAsRepairedValue() != null ? object.getAsRepairedValue() : update.getAsRepairedValue());
        update.setInspectionPhotos(object.getInspectionPhotos() != null ? object.getInspectionPhotos() : update.getInspectionPhotos());
        update.setRequiredRepairsNoted(object.getRequiredRepairsNoted() != null ? object.getRequiredRepairsNoted() : update.getRequiredRepairsNoted());
        update.setValuationMethod(object.getValuationMethod() != null ? object.getValuationMethod() : update.getValuationMethod());
        update.setDateSent(object.getDateSent() != null ? object.getDateSent() : update.getDateSent());
        update.setReportSentTo(object.getReportSentTo() != null ? object.getReportSentTo() : update.getReportSentTo());
        update.setHoaCompanyName(object.getHoaCompanyName() != null ? object.getHoaCompanyName() : update.getHoaCompanyName());
        update.setHoaInterviewDateProposal(object.getHoaInterviewDateProposal() != null ? object.getHoaInterviewDateProposal() : update.getHoaInterviewDateProposal());
        update.setPreferredClosingLocation(object.getPreferredClosingLocation() != null ? object.getPreferredClosingLocation() : update.getPreferredClosingLocation());
        update.setRequireElevationCertificate(object.getRequireElevationCertificate() != null ? object.getRequireElevationCertificate() : update.getRequireElevationCertificate());
        update.setElevationCertificate(object.getElevationCertificate() != null ? object.getElevationCertificate() : update.getElevationCertificate());
        update.setOutstandingCodeViolations(object.getOutstandingCodeViolations() != null ? object.getOutstandingCodeViolations() : update.getOutstandingCodeViolations());
        update.setTaxBillOrAmount(object.getTaxBillOrAmount() != null ? object.getTaxBillOrAmount() : update.getTaxBillOrAmount());
        update.setSellerUploadGovernmentIssuedId(object.getSellerUploadGovernmentIssuedId() != null ? object.getSellerUploadGovernmentIssuedId() : update.getSellerUploadGovernmentIssuedId());

        update.setWhOwnershipType(object.getWhOwnershipType() != null ? object.getWhOwnershipType() : update.getWhOwnershipType());
        update.setWhwireAccountHolderName(object.getWhwireAccountHolderName() != null ? object.getWhwireAccountHolderName() : update.getWhwireAccountHolderName());
        update.setWhwireAccountNumber(object.getWhwireAccountNumber() != null ? object.getWhwireAccountNumber() : update.getWhwireAccountNumber());
        update.setWhwireRoutingNumber(object.getWhwireRoutingNumber() != null ? object.getWhwireRoutingNumber() : update.getWhwireRoutingNumber());
        update.setWhZelleEmailorPhone(object.getWhZelleEmailorPhone() != null ? object.getWhZelleEmailorPhone() : update.getWhZelleEmailorPhone());
        update.setOriginalContractClosingDate(object.getOriginalContractClosingDate() != null ? object.getOriginalContractClosingDate() : update.getOriginalContractClosingDate());

        update.setUpdatedAt(LocalDateTime.now());
        repositoryCommand.save(update);
    }

    @Override
    public void delete(UUID id) {
        try {
            this.findById(id);
            repositoryCommand.deleteById(id);
        } catch (Exception e) {
            throw new NotDeleteException();
        }
    }

    @Override
    public AdquisitionPropertyDto findById(UUID id) {
        Optional<AdquisitionProperty> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get().toAggregate();
        }
        throw new AddressNotFoundException(id);
    }

    private AdquisitionProperty findByIdSimple(UUID id) {
        Optional<AdquisitionProperty> entity = repositoryQuery.findById(id);
        if (entity.isPresent()) {
            return entity.get();
        }
        throw new AddressNotFoundException(id);
    }

    @Override
    public PaginatedResponse search(Pageable pageable, List<FilterCriteria> filterCriteria) {
        GenericSpecificationsBuilder<AdquisitionProperty> specifications = new GenericSpecificationsBuilder<>(filterCriteria);
        Page<AdquisitionProperty> data = this.repositoryQuery.findAll(specifications, pageable);

        return getPaginatedResponse(data);
    }

    private PaginatedResponse getPaginatedResponse(Page<AdquisitionProperty> data) {
        List<AdquisitionPropertyResponse> objects = new ArrayList<>();
        for (AdquisitionProperty p : data.getContent()) {
            objects.add(new AdquisitionPropertyResponse(p.toAggregate()));
        }
        return new PaginatedResponse(objects, data.getTotalPages(), data.getNumberOfElements(),
                data.getTotalElements(), data.getSize(), data.getNumber());
    }

    @Override
    public List<AdquisitionPropertyDocumentDto> findByPropertyId(String propertyId) {
        List<AdquisitionProperty> entity = repositoryQuery.findByPropertyId(propertyId);
        if (!entity.isEmpty()) {
            List<AdquisitionPropertyDocumentDto> list = new ArrayList<>();
            for (AdquisitionProperty adquisitionProperty : entity) {
                list.add(adquisitionProperty.toAggregateByPropertyId());
            }
            return list;
        }
        throw new PurchaseForPropertyNotFoundException();
    }

    @Override
    public boolean existsByPropertyId(String propertyId) {
        return this.repositoryQuery.existsByPropertyId(propertyId);
    }

    private AdquisitionPropertyHoa createAdquisitionPropertyHoa(AdquisitionPropertyDto object, AdquisitionProperty update) {
        return update.getAdquisitionPropertyHoa() != null
                ? //Si es diferente de null, es porque ya tiene datos.
                AdquisitionPropertyHoa
                        .builder()
                        .id(update.getAdquisitionPropertyHoa().getId())
                        .adquisitionProperty(update)
                        .hoaTotalUnits(object.getHoaTotalUnits() != null ? object.getHoaTotalUnits() : update.getAdquisitionPropertyHoa().getHoaTotalUnits())
                        .hoaDeclarationOfCondominium(object.getHoaDeclarationOfCondominium() != null ? object.getHoaDeclarationOfCondominium() : update.getAdquisitionPropertyHoa().getHoaDeclarationOfCondominium())
                        .hoaCondominiumRider(object.getHoaCondominiumRider() != null ? object.getHoaCondominiumRider() : update.getAdquisitionPropertyHoa().getHoaCondominiumRider())
                        .hoaBylaws(object.getHoaBylaws() != null ? object.getHoaBylaws() : update.getAdquisitionPropertyHoa().getHoaBylaws())
                        .hoaLatestApprovedBudget(object.getHoaLatestApprovedBudget() != null ? object.getHoaLatestApprovedBudget() : update.getAdquisitionPropertyHoa().getHoaLatestApprovedBudget())
                        .hoaReserveStudy(object.getHoaReserveStudy() != null ? object.getHoaReserveStudy() : update.getAdquisitionPropertyHoa().getHoaReserveStudy())
                        .hoaCurrentSpecialAssessmentDisclosure(object.getHoaCurrentSpecialAssessmentDisclosure() != null ? object.getHoaCurrentSpecialAssessmentDisclosure() : update.getAdquisitionPropertyHoa().getHoaCurrentSpecialAssessmentDisclosure())
                        .hoaPendingLawsuits(object.getHoaPendingLawsuits() != null ? object.getHoaPendingLawsuits() : update.getAdquisitionPropertyHoa().getHoaPendingLawsuits())
                        .hoaDelinquencyReport(object.getHoaDelinquencyReport() != null ? object.getHoaDelinquencyReport() : update.getAdquisitionPropertyHoa().getHoaDelinquencyReport())
                        .hoaParkingAssignment(object.getHoaParkingAssignment() != null ? object.getHoaParkingAssignment() : update.getAdquisitionPropertyHoa().getHoaParkingAssignment())
                        .hoaCondoQuestionnaireForm(object.getHoaCondoQuestionnaireForm() != null ? object.getHoaCondoQuestionnaireForm() : update.getAdquisitionPropertyHoa().getHoaCondoQuestionnaireForm())
                        .buyerCreditReport(object.getBuyerCreditReport() != null ? object.getBuyerCreditReport() : update.getAdquisitionPropertyHoa().getBuyerCreditReport())
                        .hoaValidatorWebsite(object.getHoaValidatorWebsite() != null ? object.getHoaValidatorWebsite() : update.getAdquisitionPropertyHoa().getHoaValidatorWebsite())
                        .hoaApplicationLink(object.getHoaApplicationLink() != null ? object.getHoaApplicationLink() : update.getAdquisitionPropertyHoa().getHoaApplicationLink())
                        .hoaApplicationLink(object.getHoaApplicationLink() != null ? object.getHoaApplicationLink() : update.getAdquisitionPropertyHoa().getHoaApplicationLink())
                        .build()
                : AdquisitionPropertyHoa
                        .builder()
                        .id(UUID.randomUUID())
                        .adquisitionProperty(update)
                        .hoaTotalUnits(object.getHoaTotalUnits())
                        .hoaDeclarationOfCondominium(object.getHoaDeclarationOfCondominium())
                        .hoaCondominiumRider(object.getHoaCondominiumRider())
                        .hoaBylaws(object.getHoaBylaws())
                        .hoaLatestApprovedBudget(object.getHoaLatestApprovedBudget())
                        .hoaReserveStudy(object.getHoaReserveStudy())
                        .hoaCurrentSpecialAssessmentDisclosure(object.getHoaCurrentSpecialAssessmentDisclosure())
                        .hoaPendingLawsuits(object.getHoaPendingLawsuits())
                        .hoaDelinquencyReport(object.getHoaDelinquencyReport())
                        .hoaParkingAssignment(object.getHoaParkingAssignment())
                        .hoaCondoQuestionnaireForm(object.getHoaCondoQuestionnaireForm())
                        .buyerCreditReport(object.getBuyerCreditReport())
                        .hoaValidatorWebsite(object.getHoaValidatorWebsite())
                        .hoaApplicationLink(object.getHoaApplicationLink())
                        .hoaApplicationLink(object.getHoaApplicationLink())
                        .build();
    }

    private AdquisitionPropertyBuyer createAdquisitionPropertyBuyer(AdquisitionPropertyDto object, AdquisitionProperty update) {
        return update.getAdquisitionPropertyBuyer() != null
                ? //Si es diferente de null, es porque ya tiene datos.
                AdquisitionPropertyBuyer
                        .builder()
                        .id(update.getAdquisitionPropertyBuyer().getId())
                        .adquisitionProperty(update)
                        .buyerProofOfFunds(object.getBuyerProofOfFunds() != null ? object.getBuyerProofOfFunds() : update.getAdquisitionPropertyBuyer().getBuyerProofOfFunds())
                        .buyerCarBrand(object.getBuyerCarBrand() != null ? object.getBuyerCarBrand() : update.getAdquisitionPropertyBuyer().getBuyerCarBrand())
                        .buyerCarYear(object.getBuyerCarYear() != null ? object.getBuyerCarYear() : update.getAdquisitionPropertyBuyer().getBuyerCarYear())
                        .buyerDriverLicense(object.getBuyerDriverLicense() != null ? object.getBuyerDriverLicense() : update.getAdquisitionPropertyBuyer().getBuyerDriverLicense())
                        .buyerCarInsurance(object.getBuyerCarInsurance() != null ? object.getBuyerCarInsurance() : update.getAdquisitionPropertyBuyer().getBuyerCarInsurance())
                        .buyerBankName(object.getBuyerBankName() != null ? object.getBuyerBankName() : update.getAdquisitionPropertyBuyer().getBuyerBankName())
                        .buyerPersonalVoidCheck(object.getBuyerPersonalVoidCheck() != null ? object.getBuyerPersonalVoidCheck() : update.getAdquisitionPropertyBuyer().getBuyerPersonalVoidCheck())
                        .build()
                : AdquisitionPropertyBuyer
                        .builder()
                        .id(UUID.randomUUID())
                        .adquisitionProperty(update)
                        .buyerProofOfFunds(object.getBuyerProofOfFunds())
                        .buyerCarBrand(object.getBuyerCarBrand())
                        .buyerCarYear(object.getBuyerCarYear())
                        .buyerDriverLicense(object.getBuyerDriverLicense())
                        .buyerCarInsurance(object.getBuyerCarInsurance())
                        .buyerBankName(object.getBuyerBankName())
                        .buyerPersonalVoidCheck(object.getBuyerPersonalVoidCheck())
                        .build();
    }

    private AdquisitionPropertyBuyerPersonalBankInfo createAdquisitionPropertyBuyerPersonalBankInfo(AdquisitionPropertyDto object, AdquisitionProperty update) {
        return update.getAdquisitionPropertyBuyerPersonalBankInfo() != null
                ? //Si es diferente de null, es porque ya tiene datos.
                AdquisitionPropertyBuyerPersonalBankInfo
                        .builder()
                        .id(update.getAdquisitionPropertyBuyerPersonalBankInfo().getId())
                        .adquisitionProperty(update)
                        .buyerPersonalAccountHolderName(object.getBuyerPersonalAccountHolderName() != null ? object.getBuyerPersonalAccountHolderName() : update.getAdquisitionPropertyBuyerPersonalBankInfo().getBuyerPersonalAccountHolderName())
                        .buyerPersonalAccountNumber(object.getBuyerPersonalAccountNumber() != null ? object.getBuyerPersonalAccountNumber() : update.getAdquisitionPropertyBuyerPersonalBankInfo().getBuyerPersonalAccountNumber())
                        .buyerPersonalRoutingNumber(object.getBuyerPersonalRoutingNumber() != null ? object.getBuyerPersonalRoutingNumber() : update.getAdquisitionPropertyBuyerPersonalBankInfo().getBuyerPersonalRoutingNumber())
                        .buyerPersonalZelleEmailorPhone(object.getBuyerPersonalZelleEmailorPhone() != null ? object.getBuyerPersonalZelleEmailorPhone() : update.getAdquisitionPropertyBuyerPersonalBankInfo().getBuyerPersonalZelleEmailorPhone())
                        .buyerPersonalBankStatements(object.getBuyerPersonalBankStatements() != null ? object.getBuyerPersonalBankStatements() : update.getAdquisitionPropertyBuyerPersonalBankInfo().getBuyerPersonalBankStatements())
                        .buyerPersonalBankName(object.getBuyerPersonalBankName() != null ? object.getBuyerPersonalBankName() : update.getAdquisitionPropertyBuyerPersonalBankInfo().getBuyerPersonalBankName())
                        .buyerPersonalUseForHoaBankReference(object.getBuyerPersonalUseForHoaBankReference() != null ? object.getBuyerPersonalUseForHoaBankReference() : update.getAdquisitionPropertyBuyerPersonalBankInfo().getBuyerPersonalUseForHoaBankReference())
                        .buyerPersonalUseForLenderBankReference(object.getBuyerPersonalUseForLenderBankReference() != null ? object.getBuyerPersonalUseForLenderBankReference() : update.getAdquisitionPropertyBuyerPersonalBankInfo().getBuyerPersonalUseForLenderBankReference())
                        .buyerVoidCheck(object.getBuyerVoidCheck() != null ? object.getBuyerVoidCheck() : update.getAdquisitionPropertyBuyerPersonalBankInfo().getBuyerVoidCheck())
                        .buyerLegalEntityUseForHoaBankReference(object.getBuyerLegalEntityUseForHoaBankReference() != null ? object.getBuyerLegalEntityUseForHoaBankReference() : update.getAdquisitionPropertyBuyerPersonalBankInfo().getBuyerLegalEntityUseForHoaBankReference())
                        .build()
                : AdquisitionPropertyBuyerPersonalBankInfo
                        .builder()
                        .id(UUID.randomUUID())
                        .adquisitionProperty(update)
                        .buyerPersonalAccountHolderName(object.getBuyerPersonalAccountHolderName())
                        .buyerPersonalAccountNumber(object.getBuyerPersonalAccountNumber())
                        .buyerPersonalRoutingNumber(object.getBuyerPersonalRoutingNumber())
                        .buyerPersonalZelleEmailorPhone(object.getBuyerPersonalZelleEmailorPhone())
                        .buyerPersonalBankStatements(object.getBuyerPersonalBankStatements())
                        .buyerPersonalBankName(object.getBuyerPersonalBankName())
                        .buyerPersonalUseForHoaBankReference(object.getBuyerPersonalUseForHoaBankReference())
                        .buyerPersonalUseForLenderBankReference(object.getBuyerPersonalUseForLenderBankReference())
                        .build();
    }

    private AdquisitionPropertyBuyerUtilitiesInfo createAdquisitionPropertyBuyerUtilitiesInfo(AdquisitionPropertyDto object, AdquisitionProperty update) {
        return update.getAdquisitionPropertyBuyerUtilitiesInfo() != null
                ? //Si es diferente de null, es porque ya tiene datos.
                AdquisitionPropertyBuyerUtilitiesInfo
                        .builder()
                        .id(update.getAdquisitionPropertyBuyerUtilitiesInfo().getId())
                        .adquisitionProperty(update)
                        .buyerElectricProviderAccount(object.getBuyerElectricProviderAccount() != null ? object.getBuyerElectricProviderAccount() : update.getAdquisitionPropertyBuyerUtilitiesInfo().getBuyerElectricProviderAccount())
                        .buyerGasServiceAccount(object.getBuyerGasServiceAccount() != null ? object.getBuyerGasServiceAccount() : update.getAdquisitionPropertyBuyerUtilitiesInfo().getBuyerGasServiceAccount())
                        .buyerTrashServiceAccount(object.getBuyerTrashServiceAccount() != null ? object.getBuyerTrashServiceAccount() : update.getAdquisitionPropertyBuyerUtilitiesInfo().getBuyerTrashServiceAccount())
                        .buyerWaterSewerSetupAccount(object.getBuyerWaterSewerSetupAccount() != null ? object.getBuyerWaterSewerSetupAccount() : update.getAdquisitionPropertyBuyerUtilitiesInfo().getBuyerWaterSewerSetupAccount())
                        .buyerInternetService(object.getBuyerInternetService() != null ? object.getBuyerInternetService() : update.getAdquisitionPropertyBuyerUtilitiesInfo().getBuyerInternetService())
                        .buyerNotes(object.getBuyerNotes() != null ? object.getBuyerNotes() : update.getAdquisitionPropertyBuyerUtilitiesInfo().getBuyerNotes())
                        .buyerStartServiceDate(object.getBuyerStartServiceDate() != null ? object.getBuyerStartServiceDate() : update.getAdquisitionPropertyBuyerUtilitiesInfo().getBuyerStartServiceDate())
                        .buyerDepositAmount(object.getBuyerDepositAmount() != null ? object.getBuyerDepositAmount() : update.getAdquisitionPropertyBuyerUtilitiesInfo().getBuyerDepositAmount())

                        .gasBuyerStartServiceDate(object.getGasBuyerStartServiceDate() != null ? object.getGasBuyerStartServiceDate() : update.getAdquisitionPropertyBuyerUtilitiesInfo().getGasBuyerStartServiceDate())
                        .gasBuyerDepositAmount(object.getGasBuyerDepositAmount() != null ? object.getGasBuyerDepositAmount() : update.getAdquisitionPropertyBuyerUtilitiesInfo().getGasBuyerDepositAmount())
                        .trashBuyerStartServiceDate(object.getTrashBuyerStartServiceDate() != null ? object.getTrashBuyerStartServiceDate() : update.getAdquisitionPropertyBuyerUtilitiesInfo().getTrashBuyerStartServiceDate())
                        .trashBuyerDepositAmount(object.getTrashBuyerDepositAmount() != null ? object.getTrashBuyerDepositAmount() : update.getAdquisitionPropertyBuyerUtilitiesInfo().getTrashBuyerDepositAmount())
                        .waterBuyerStartServiceDate(object.getWaterBuyerStartServiceDate() != null ? object.getWaterBuyerStartServiceDate() : update.getAdquisitionPropertyBuyerUtilitiesInfo().getWaterBuyerStartServiceDate())
                        .waterBuyerDepositAmount(object.getWaterBuyerDepositAmount() != null ? object.getWaterBuyerDepositAmount() : update.getAdquisitionPropertyBuyerUtilitiesInfo().getWaterBuyerDepositAmount())
                        .internetBuyerStartServiceDate(object.getInternetBuyerStartServiceDate() != null ? object.getInternetBuyerStartServiceDate() : update.getAdquisitionPropertyBuyerUtilitiesInfo().getInternetBuyerStartServiceDate())
                        .internetBuyerDepositAmount(object.getInternetBuyerDepositAmount() != null ? object.getInternetBuyerDepositAmount() : update.getAdquisitionPropertyBuyerUtilitiesInfo().getInternetBuyerDepositAmount())
                        .build()
                : AdquisitionPropertyBuyerUtilitiesInfo
                        .builder()
                        .id(UUID.randomUUID())
                        .adquisitionProperty(update)
                        .buyerElectricProviderAccount(object.getBuyerElectricProviderAccount())
                        .buyerGasServiceAccount(object.getBuyerGasServiceAccount())
                        .buyerTrashServiceAccount(object.getBuyerTrashServiceAccount())
                        .buyerWaterSewerSetupAccount(object.getBuyerWaterSewerSetupAccount())
                        .buyerInternetService(object.getBuyerInternetService())
                        .buyerNotes(object.getBuyerNotes())
                        .buyerStartServiceDate(object.getBuyerStartServiceDate())
                        .buyerDepositAmount(object.getBuyerDepositAmount())

                        .gasBuyerStartServiceDate(object.getGasBuyerStartServiceDate())
                        .gasBuyerDepositAmount(object.getGasBuyerDepositAmount())
                        .trashBuyerStartServiceDate(object.getTrashBuyerStartServiceDate())
                        .trashBuyerDepositAmount(object.getTrashBuyerDepositAmount())
                        .waterBuyerStartServiceDate(object.getWaterBuyerStartServiceDate())
                        .waterBuyerDepositAmount(object.getWaterBuyerDepositAmount())
                        .internetBuyerStartServiceDate(object.getInternetBuyerStartServiceDate())
                        .internetBuyerDepositAmount(object.getInternetBuyerDepositAmount())
                        .build();
    }

    @Override
    public void updateBankStatementRequest(UUID id, String bankStatementRequest) {
        AdquisitionProperty update = this.findByIdSimple(id);
        update.setBankStatementRequest(bankStatementRequest);
        repositoryCommand.save(update);
    }

}
