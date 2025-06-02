package com.kynsoft.medicaltest.application.command.labtest.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.medicaltest.domain.service.ILabTestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Manejador para el comando de eliminación de examen de laboratorio
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DeleteLabTestCommandHandler implements ICommandHandler<DeleteLabTestCommand> {

    private final ILabTestService labTestService;

    @Override
    public void handle(DeleteLabTestCommand command) {
        log.info("Eliminando examen de laboratorio con ID: {}", command.getId());
        labTestService.delete(command.getId());
    }
}
