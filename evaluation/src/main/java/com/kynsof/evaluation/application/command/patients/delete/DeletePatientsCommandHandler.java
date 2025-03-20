package com.kynsof.evaluation.application.command.patients.delete;

import com.kynsof.evaluation.infrastructure.service.PatientsServiceImpl;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
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
