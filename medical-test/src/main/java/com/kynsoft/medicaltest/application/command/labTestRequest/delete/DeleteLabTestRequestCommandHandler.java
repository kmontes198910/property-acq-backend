package com.kynsoft.medicaltest.application.command.labTestRequest.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.medicaltest.domain.service.ILabTestRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeleteLabTestRequestCommandHandler implements ICommandHandler<DeleteLabTestRequestCommand> {

    private final ILabTestRequestService labTestRequestService ;

    @Override
    public void handle(DeleteLabTestRequestCommand command) {
        log.info("Eliminando examen de laboratorio con ID: {}", command.getId());
        labTestRequestService.delete(command.getId());
    }
}
