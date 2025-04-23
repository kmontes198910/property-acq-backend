package com.kynsof.treatments.application.command.result.delete;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.treatments.application.service.ResultServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DeleteResultCommandHandler implements ICommandHandler<DeleteResultCommand> {

    private final ResultServiceImpl resultService;

    @Override
    public void handle(DeleteResultCommand command) {
        resultService.delete(command.getId());
    }
}