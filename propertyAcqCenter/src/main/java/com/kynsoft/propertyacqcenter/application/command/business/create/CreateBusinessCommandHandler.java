package com.kynsoft.propertyacqcenter.application.command.business.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.propertyacqcenter.domain.dto.BusinessDto;
import com.kynsoft.propertyacqcenter.domain.dto.exception.business.BusinessNameMustBeUniqueException;
import com.kynsoft.propertyacqcenter.domain.services.IBusinessService;
import org.springframework.stereotype.Component;

@Component
public class CreateBusinessCommandHandler implements ICommandHandler<CreateBusinessCommand> {

    private final IBusinessService businessService;

    public CreateBusinessCommandHandler(IBusinessService businessService) {
        this.businessService = businessService;
    }

    @Override
    public void handle(CreateBusinessCommand command) {
        this.validateName(command.getName());
        businessService.create(new BusinessDto(command.getId(), command.getName()));
    }

    private void validateName(String name) {
        if (this.businessService.countByName(name) > 0) {
            throw new BusinessNameMustBeUniqueException(name);
        }
    }

}
