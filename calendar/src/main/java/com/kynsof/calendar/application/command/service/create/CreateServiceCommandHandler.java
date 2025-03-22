package com.kynsof.calendar.application.command.service.create;

import com.kynsof.calendar.domain.dto.ServiceDto;
import com.kynsof.calendar.domain.dto.ServiceTypeDto;
import com.kynsof.calendar.domain.rules.service.ServiceCodeMustBeUniqueRule;
import com.kynsof.calendar.domain.rules.service.SeviceNameMustBeUniqueRule;
import com.kynsof.calendar.domain.service.IServiceService;
import com.kynsof.calendar.domain.service.IServiceTypeService;
import com.kynsof.share.core.domain.RulesChecker;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class CreateServiceCommandHandler implements ICommandHandler<CreateServiceCommand> {

    private final IServiceService service;
    private final IServiceTypeService serviceTypeService;

    public CreateServiceCommandHandler(IServiceService service, 
                                       IServiceTypeService serviceTypeService) {
        this.service = service;
        this.serviceTypeService = serviceTypeService;
    }

    @Override
    public void handle(CreateServiceCommand command) {
        RulesChecker.checkRule(new SeviceNameMustBeUniqueRule(this.service, command.getName(), command.getId()));
        RulesChecker.checkRule(new ServiceCodeMustBeUniqueRule(this.service, command.getCode(), command.getId()));
        ServiceTypeDto serviceTypeDto = serviceTypeService.findById(command.getType());

        ServiceDto serviceDto = service.create(new ServiceDto(
                command.getId(),
                serviceTypeDto,
                command.getStatus(),
                command.getImage(),
                command.getName(),
                0.00,
                command.getDescription(),
                command.isApplyIva(),
                command.getEstimatedDuration(),
                command.getCode()
        ));

        command.setId(serviceDto.getId());

    }
}
