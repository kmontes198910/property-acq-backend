package com.kynsoft.medicaltest.application.command.labTestItemRequest.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.medicaltest.domain.service.ILabTestItemRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DeleteLabTestItemRequestCommandHandler implements ICommandHandler<DeleteLabTestItemRequestCommand> {

    private final ILabTestItemRequestService labTestItemRequestService;

    @Override
    public void handle(DeleteLabTestItemRequestCommand command) {
        labTestItemRequestService.delete(command.getId());
    }
}
