package com.kynsoft.propertyacqcenter.application.command.insuranceBroker.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.InsuranceBrokerDto;
import com.kynsoft.propertyacqcenter.domain.dto.LegalEntityDto;
import com.kynsoft.propertyacqcenter.domain.dto.PropertyDto;
import com.kynsoft.propertyacqcenter.domain.services.IInsuranceBrokerService;
import com.kynsoft.propertyacqcenter.domain.services.ILegalEntityService;
import com.kynsoft.propertyacqcenter.domain.services.IPropertyService;
import org.springframework.stereotype.Component;

@Component
public class CreateInsuranceBrokerCommandHandler implements ICommandHandler<CreateInsuranceBrokerCommand> {

    private final IInsuranceBrokerService insuranceBrokerService;
    private final ILegalEntityService legalEntityService;
    private final IPropertyService propertyService;

    public CreateInsuranceBrokerCommandHandler(IInsuranceBrokerService insuranceBrokerService, ILegalEntityService legalEntityService, IPropertyService propertyService) {
        this.insuranceBrokerService = insuranceBrokerService;
        this.legalEntityService = legalEntityService;
        this.propertyService = propertyService;
    }

    @Override
    public void handle(CreateInsuranceBrokerCommand command) {
        LegalEntityDto sellerName = this.legalEntityService.findById(command.getBuyer());
        PropertyDto property = this.propertyService.getById(command.getProperty());

        this.insuranceBrokerService.create(InsuranceBrokerDto.builder()
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
