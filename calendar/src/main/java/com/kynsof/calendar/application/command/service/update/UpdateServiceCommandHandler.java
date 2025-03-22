package com.kynsof.calendar.application.command.service.update;

import com.kynsof.calendar.domain.dto.ServiceDto;
import com.kynsof.calendar.domain.dto.ServiceTypeDto;
import com.kynsof.calendar.domain.rules.service.SeviceNameMustBeUniqueRule;
import com.kynsof.calendar.domain.service.IServiceService;
import com.kynsof.calendar.domain.service.IServiceTypeService;
import com.kynsof.share.core.domain.RulesChecker;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.rules.ValidateObjectNotNullRule;
import com.kynsof.share.utils.UpdateIfNotNull;
import org.springframework.stereotype.Component;

@Component
public class UpdateServiceCommandHandler implements ICommandHandler<UpdateServiceCommand> {

    private final IServiceService service;
    private final IServiceTypeService serviceTypeService;

    public UpdateServiceCommandHandler(IServiceService service, IServiceTypeService serviceTypeService) {
        this.service = service;
        this.serviceTypeService = serviceTypeService;
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


        service.update(update);

    }
}
