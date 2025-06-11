package com.kynsoft.settings.application.command.serviceType.create;


import com.kynsof.share.core.domain.RulesChecker;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.settings.domain.dto.ServiceTypeDto;
import com.kynsoft.settings.domain.rules.serviceType.ServiceTypeCodeMustBeUniqueRule;
import com.kynsoft.settings.domain.rules.serviceType.SeviceTypeNameMustBeUniqueRule;
import com.kynsoft.settings.domain.services.IServiceTypeService;
import com.kynsoft.settings.infrastructure.services.rabbitMq.eventPublisher.EventServiceTypePublisherService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreateServiceTypeCommandHandler implements ICommandHandler<CreateServiceTypeCommand> {

    private final IServiceTypeService service;
    private final EventServiceTypePublisherService eventServiceTypePublisherService;

    public CreateServiceTypeCommandHandler(IServiceTypeService service, EventServiceTypePublisherService eventServiceTypePublisherService) {
        this.service = service;
        this.eventServiceTypePublisherService = eventServiceTypePublisherService;
    }

    @Override
    public void handle(CreateServiceTypeCommand command) {
        RulesChecker.checkRule(new SeviceTypeNameMustBeUniqueRule(this.service, command.getName(), command.getId()));
        RulesChecker.checkRule(new ServiceTypeCodeMustBeUniqueRule(this.service, command.getCode(), command.getId()));

        ServiceTypeDto type  = service.create(new ServiceTypeDto(
                command.getId(),
                command.getName(),
                command.getPicture(),
                command.getStatus(),
                command.getCode()
        ));
        command.setId(type.getId());
        this.eventServiceTypePublisherService.publishServiceTypeEvent(type);
        // this.producerServiceTypeEventService.create(new ServiceTypeKafka(type.getId(), type.getName(), type.getPicture(), type.getStatus().name(), type.getCode()));
    }
}
