package com.kynsof.patients.application.command.patients.patientsKeyCloack;

import com.kynsof.patients.domain.service.IPatientsService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class patientsKeyCloackCommandHandler implements ICommandHandler<patientsKeyCloakCommand> {

    private final IPatientsService serviceImpl;


    public patientsKeyCloackCommandHandler(IPatientsService serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(patientsKeyCloakCommand command) {
        serviceImpl.updateKeyCloak(command.getId(), command.getKeyCloackId());

    }
}
