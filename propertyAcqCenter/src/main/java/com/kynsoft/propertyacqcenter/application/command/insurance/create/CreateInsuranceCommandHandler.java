package com.kynsoft.propertyacqcenter.application.command.insurance.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.InsuranceDto;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.services.IInsuranceService;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import org.springframework.stereotype.Component;

@Component
public class CreateInsuranceCommandHandler implements ICommandHandler<CreateInsuranceCommand> {

    private final IInsuranceService insuranceService;
    private final ILegalEntityService legalEntityService;

    public CreateInsuranceCommandHandler(IInsuranceService insuranceService, ILegalEntityService legalEntityService) {
        this.insuranceService = insuranceService;
        this.legalEntityService = legalEntityService;
    }

    @Override
    public void handle(CreateInsuranceCommand command) {
        LegalEntityDto legalEntityDto = this.legalEntityService.findById(command.getLegalEntity());
        insuranceService.create(new InsuranceDto(
                command.getId(), 
                command.getInsuranceType(), 
                command.getDocument(), 
                legalEntityDto, 
                null, 
                null
        ));
    }
}
