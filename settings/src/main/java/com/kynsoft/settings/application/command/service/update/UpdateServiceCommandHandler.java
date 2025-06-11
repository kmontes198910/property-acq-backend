package com.kynsoft.settings.application.command.service.update;


import com.kynsof.share.core.domain.RulesChecker;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.rules.ValidateObjectNotNullRule;
import com.kynsof.share.utils.UpdateIfNotNull;
import com.kynsoft.settings.domain.dto.ServiceDto;
import com.kynsoft.settings.domain.dto.ServiceTypeDto;
import com.kynsoft.settings.domain.rules.service.SeviceNameMustBeUniqueRule;
import com.kynsoft.settings.domain.services.IServiceService;
import com.kynsoft.settings.domain.services.IServiceTypeService;
import com.kynsoft.settings.infrastructure.services.rabbitMq.eventPublisher.EventServicePublisherService;
import org.springframework.stereotype.Component;

@Component
public class UpdateServiceCommandHandler implements ICommandHandler<UpdateServiceCommand> {

    private final IServiceService service;
    private final IServiceTypeService serviceTypeService;
    private final EventServicePublisherService eventServicePublisherService;

    public UpdateServiceCommandHandler(IServiceService service, IServiceTypeService serviceTypeService, EventServicePublisherService eventServicePublisherService) {
        this.service = service;
        this.serviceTypeService = serviceTypeService;
        this.eventServicePublisherService = eventServicePublisherService;
    }

    @Override
    public void handle(UpdateServiceCommand command) {
        RulesChecker.checkRule(new ValidateObjectNotNullRule<>(command.getServiceTypeId(), "id", "Service Type ID cannot be null."));
        RulesChecker.checkRule(new ValidateObjectNotNullRule<>(command.getId(), "id", "Service ID cannot be null."));
        RulesChecker.checkRule(new SeviceNameMustBeUniqueRule(this.service, command.getName(), command.getId()));

        ServiceTypeDto serviceTypeDto = serviceTypeService.findById(command.getServiceTypeId());
        ServiceDto update = service.findByIds(command.getId());
        update.setType(serviceTypeDto);
        update.setEstimatedDuration(command.getEstimatedDuration());
        update.setStatus(command.getStatus());
        update.setPicture(command.getPicture());

        update.setApplyIva(command.isApplyIva());

        //update.setNormalAppointmentPrice(command.getNormalAppointmentPrice());

        UpdateIfNotNull.updateIfStringNotNull(update::setName, command.getName());
        UpdateIfNotNull.updateIfStringNotNull(update::setDescription, command.getDescription());


        ServiceDto serviceDto = service.update(update);
        this.eventServicePublisherService.publishServiceEvent(serviceDto);

    }
}