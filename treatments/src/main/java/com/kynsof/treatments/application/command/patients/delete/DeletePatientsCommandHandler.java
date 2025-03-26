package com.kynsof.treatments.application.command.patients.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.treatments.application.service.PatientsServiceImpl;
import org.springframework.stereotype.Component;

@Component
public class DeletePatientsCommandHandler implements ICommandHandler<PatientsDeleteCommand> {

    private final PatientsServiceImpl serviceImpl;

    public DeletePatientsCommandHandler(PatientsServiceImpl serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    @Override
    public void handle(PatientsDeleteCommand command) {

        serviceImpl.delete(command.getId());
    }

}
