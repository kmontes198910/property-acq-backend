package com.kynsoft.propertyacqcenter.application.command.acquisitionDetails.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.AcquisitionDetailsDto;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.services.IAcquisitionDetailsService;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import org.springframework.stereotype.Component;

@Component
public class UpdateAcquisitionDetailsCommandHandler implements ICommandHandler<UpdateAcquisitionDetailsCommand> {

    private final IAcquisitionDetailsService acquisitionDetailsService;
    private final ILegalEntityService legalEntityService;

    public UpdateAcquisitionDetailsCommandHandler(IAcquisitionDetailsService acquisitionDetailsService, ILegalEntityService legalEntityService) {
        this.acquisitionDetailsService = acquisitionDetailsService;
        this.legalEntityService = legalEntityService;
    }

    @Override
    public void handle(UpdateAcquisitionDetailsCommand command) {
        LegalEntityDto sellerName = this.legalEntityService.findById(command.getSellerName());
        LegalEntityDto sellerContactInfo = this.legalEntityService.findById(command.getSellerContactInfo());
        
        this.acquisitionDetailsService.update(AcquisitionDetailsDto.builder()
                .id(command.getId())
                .contractExecutionDate(command.getContractExecutionDate())
                .acquisitionType(command.getAcquisitionType())
                .sourceType(command.getSourceType())
                .sellerName(sellerName)
                .sellerContactInfo(sellerContactInfo)
                .askingPrice(command.getAskingPrice())
                .purchasePrice(command.getPurchasePrice())
                .expectedClosingDate(command.getExpectedClosingDate())
                .rentalPrice(command.getRentalPrice())
                .emdRequirements(command.getEmdRequirements())
                .emdOfferedAmount(command.getEmdOfferedAmount())
                .afterRepairValue(command.getAfterRepairValue())
                .floodZoneDetermination(command.getFloodZoneDetermination())
                .propertyRented(command.getPropertyRented())
                .build()
        );
    }

}
