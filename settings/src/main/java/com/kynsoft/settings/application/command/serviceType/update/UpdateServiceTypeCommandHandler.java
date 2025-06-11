package com.kynsoft.settings.application.command.serviceType.update;


import com.kynsof.share.core.domain.RulesChecker;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.rules.ValidateObjectNotNullRule;
import com.kynsof.share.utils.UpdateIfNotNull;
import com.kynsoft.settings.domain.dto.ServiceTypeDto;
import com.kynsoft.settings.domain.services.IServiceTypeService;
import com.kynsoft.settings.infrastructure.services.rabbitMq.eventPublisher.EventServiceTypePublisherService;
import org.springframework.stereotype.Component;

@Component
public class UpdateServiceTypeCommandHandler implements ICommandHandler<UpdateServiceTypeCommand> {

    private final IServiceTypeService service;
    private final EventServiceTypePublisherService eventServiceTypePublisherService;

    public UpdateServiceTypeCommandHandler(IServiceTypeService service, EventServiceTypePublisherService eventServiceTypePublisherService) {
        this.service = service;
        this.eventServiceTypePublisherService = eventServiceTypePublisherService;
    }

    @Override
    public void handle(UpdateServiceTypeCommand command) {
        RulesChecker.checkRule(new ValidateObjectNotNullRule<>(command.getId(), "id", "Service Type ID cannot be null."));

        ServiceTypeDto update = this.service.findById(command.getId());


        UpdateIfNotNull.updateIfStringNotNull(update::setName, command.getName());
        update.setPicture(command.getPicture());
        update.setStatus(command.getStatus());
        ServiceTypeDto updated = service.update(update);

        this.eventServiceTypePublisherService.publishServiceTypeEvent(updated);
        // this.producerServiceTypeEventService.create(new ServiceTypeKafka(update.getId(), update.getName(), update.getPicture(), update.getStatus().name(), update.getCode()));
    }
}
