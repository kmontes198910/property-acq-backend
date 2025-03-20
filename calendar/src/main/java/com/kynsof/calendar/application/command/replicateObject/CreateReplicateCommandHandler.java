package com.kynsof.calendar.application.command.replicateObject;

import com.kynsof.calendar.domain.service.IServiceService;
import com.kynsof.calendar.domain.service.IServiceTypeService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class CreateReplicateCommandHandler implements ICommandHandler<CreateReplicateCommand> {

    private final IServiceTypeService serviceTypeService;

    private final IServiceService serviceService;


    public CreateReplicateCommandHandler(IServiceTypeService serviceTypeService, IServiceService serviceService) {
        this.serviceTypeService = serviceTypeService;
        this.serviceService = serviceService;

    }

    @Override
    public void handle(CreateReplicateCommand command) {

    }
}
