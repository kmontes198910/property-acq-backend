package com.kynsoft.propertyacqcenter.application.command.adquisitionProperty.update.seller;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.embedded.adquisitionProperty.UpdateAdquisitionSellerDto;
import com.kynsoft.propertyacqcenter.domain.services.IAdquisitionPropertyService;
import org.springframework.stereotype.Component;

@Component
public class UpdateAdquisitionPropertySellerCommandHandler implements ICommandHandler<UpdateAdquisitionPropertySellerCommand> {

    private final IAdquisitionPropertyService adquisitionPropertyService;

    public UpdateAdquisitionPropertySellerCommandHandler(IAdquisitionPropertyService adquisitionPropertyService) {
        this.adquisitionPropertyService = adquisitionPropertyService;
    }

    @Override
    public void handle(UpdateAdquisitionPropertySellerCommand command) {
        adquisitionPropertyService.updateSeller(UpdateAdquisitionSellerDto
                .builder()
                .idAdquisition(command.getAdquisitionId())
                .fullName(command.getFullName())
                .entityName(command.getEntityName())
                .articlesOfIncorporation(command.getArticlesOfIncorporation() != null ? command.getArticlesOfIncorporation().getFilePath() + "|" + command.getArticlesOfIncorporation().getFileName() : null)
                .certificateOfGoodStanding(command.getCertificateOfGoodStanding() != null ? command.getCertificateOfGoodStanding().getFilePath() + "|" + command.getCertificateOfGoodStanding().getFileName() : null)
                .operatingAgreement(command.getOperatingAgreement() != null ? command.getOperatingAgreement().getFilePath() + "|" + command.getOperatingAgreement().getFileName() : null)
                .ownershipType(command.getOwnershipType())
                .resolutionToSell(command.getResolutionToSell() != null ? command.getResolutionToSell().getFilePath() + "|" + command.getResolutionToSell().getFileName() : null)
                .contactEmail(command.getContactEmail())
                .mobilePhone(command.getMobilePhone())
                .mailingAddress(command.getMailingAddress())
                .socialSecurityNumber(command.getSocialSecurityNumber())
                .maritalStatus(command.getMaritalStatus())
                .governmentId(command.getGovernmentId() != null ? command.getGovernmentId().getFilePath() + "|" + command.getGovernmentId().getFileName() : null)
                .w9Form(command.getW9Form() != null ? command.getW9Form().getFilePath() + "|" + command.getW9Form().getFileName() : null)
                .isForeignSeller(command.getIsForeignSeller())
                .firptaAffidavit(command.getFirptaAffidavit() != null ? command.getFirptaAffidavit().getFilePath() + "|" + command.getFirptaAffidavit().getFileName() : null)
                .wireAccountHolder(command.getSellerWireAccountHolder())
                .wireAccountNumber(command.getSellerWireAccountNumber())
                .wireRoutingNumber(command.getSellerWireRoutingNumber())
                .zelleContact(command.getZelleContact())
                .build()
        );
    }

}
