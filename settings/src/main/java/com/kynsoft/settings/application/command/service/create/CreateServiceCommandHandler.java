package com.kynsoft.settings.application.command.service.create;


import com.kynsof.share.core.domain.RulesChecker;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.settings.domain.dto.ServiceDto;
import com.kynsoft.settings.domain.dto.ServiceTypeDto;
import com.kynsoft.settings.domain.rules.service.ServiceCodeMustBeUniqueRule;
import com.kynsoft.settings.domain.rules.service.SeviceNameMustBeUniqueRule;
import com.kynsoft.settings.domain.services.IServiceService;
import com.kynsoft.settings.domain.services.IServiceTypeService;
import com.kynsoft.settings.infrastructure.services.rabbitMq.eventPublisher.EventServicePublisherService;
import org.springframework.stereotype.Component;

@Component
public class CreateServiceCommandHandler implements ICommandHandler<CreateServiceCommand> {

    private final IServiceService service;
    private final IServiceTypeService serviceTypeService;
    private final EventServicePublisherService eventServicePublisherService;

    public CreateServiceCommandHandler(IServiceService service,
                                       IServiceTypeService serviceTypeService, EventServicePublisherService eventServicePublisherService) {
        this.service = service;
        this.serviceTypeService = serviceTypeService;
        this.eventServicePublisherService = eventServicePublisherService;
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
        this.eventServicePublisherService.publishServiceEvent(serviceDto);
    }
}
