package com.kynsoft.propertyacqcenter.application.command.insuranceBroker.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.InsuranceBrokerDto;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.services.IInsuranceBrokerService;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import org.springframework.stereotype.Component;

@Component
public class UpdateInsuranceBrokerCommandHandler implements ICommandHandler<UpdateInsuranceBrokerCommand> {

    private final IInsuranceBrokerService insuranceBrokerService;
    private final ILegalEntityService legalEntityService;
    private final IPropertyService propertyService;

    public UpdateInsuranceBrokerCommandHandler(IInsuranceBrokerService insuranceBrokerService, ILegalEntityService legalEntityService, IPropertyService propertyService) {
        this.insuranceBrokerService = insuranceBrokerService;
        this.legalEntityService = legalEntityService;
        this.propertyService = propertyService;
    }

    @Override
    public void handle(UpdateInsuranceBrokerCommand command) {
        LegalEntityDto sellerName = this.legalEntityService.findById(command.getBuyer());
        PropertyDto property = this.propertyService.getById(command.getProperty());

        this.insuranceBrokerService.update(InsuranceBrokerDto.builder()
                .id(command.getId())
                .property(property)
                .closingDate(command.getClosingDate())
                .buyer(sellerName)
                .lenderInfo(command.getLenderInfo())
                .lenderInsurance(command.getLenderInsurance())
                .build()
        );
    }

}
