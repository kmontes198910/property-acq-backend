package com.kynsoft.medicaltest.application.command.labtestparameter.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.medicaltest.domain.service.ILabTestParameterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Manejador del comando para eliminar un parámetro de examen de laboratorio
 */
@Component
@RequiredArgsConstructor
public class DeleteLabTestParameterCommandHandler implements ICommandHandler<DeleteLabTestParameterCommand> {

    private final ILabTestParameterService labTestParameterService;

    @Override
    public void handle(DeleteLabTestParameterCommand command) {
        labTestParameterService.delete(command.getId());
    }
}
