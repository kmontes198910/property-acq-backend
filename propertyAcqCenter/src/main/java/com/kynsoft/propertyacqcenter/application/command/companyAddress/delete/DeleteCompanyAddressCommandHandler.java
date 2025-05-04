package com.kynsoft.propertyacqcenter.application.command.companyAddress.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.services.ICompanyAddressService;
import org.springframework.stereotype.Component;

@Component
public class DeleteCompanyAddressCommandHandler implements ICommandHandler<DeleteCompanyAddressCommand> {

    private final ICompanyAddressService companyAddressService;

    public DeleteCompanyAddressCommandHandler(ICompanyAddressService companyAddressService) {
        this.companyAddressService = companyAddressService;
    }

    @Override
    public void handle(DeleteCompanyAddressCommand command) {
        this.companyAddressService.delete(command.getId());
    }
}
