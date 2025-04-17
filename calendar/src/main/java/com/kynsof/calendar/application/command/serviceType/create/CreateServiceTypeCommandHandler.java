package com.kynsof.calendar.application.command.serviceType.create;

import com.kynsof.calendar.domain.dto.ServiceTypeDto;
import com.kynsof.calendar.domain.rules.servicetype.ServiceTypeCodeMustBeUniqueRule;
import com.kynsof.calendar.domain.rules.servicetype.SeviceTypeNameMustBeUniqueRule;
import com.kynsof.calendar.domain.service.IServiceTypeService;
import com.kynsof.share.core.domain.RulesChecker;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.kafka.entity.ServiceTypeKafka;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreateServiceTypeCommandHandler implements ICommandHandler<CreateServiceTypeCommand> {

    private final IServiceTypeService service;

    public CreateServiceTypeCommandHandler(IServiceTypeService service) {
        this.service = service;
    }

    @Override
    public void handle(CreateServiceTypeCommand command) {
        RulesChecker.checkRule(new SeviceTypeNameMustBeUniqueRule(this.service, command.getName(), command.getId()));
        RulesChecker.checkRule(new ServiceTypeCodeMustBeUniqueRule(this.service, command.getCode(), command.getId()));

        ServiceTypeDto type = new ServiceTypeDto(
                command.getId(),
                command.getName(),
                command.getPicture(),
                command.getStatus(),
                command.getCode()
        );
        UUID id = service.create(type);
        command.setId(id);
       // this.producerServiceTypeEventService.create(new ServiceTypeKafka(type.getId(), type.getName(), type.getPicture(), type.getStatus().name(), type.getCode()));
    }
}
