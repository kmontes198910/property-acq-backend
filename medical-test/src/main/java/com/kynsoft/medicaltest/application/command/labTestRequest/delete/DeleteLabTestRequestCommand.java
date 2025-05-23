package com.kynsoft.medicaltest.application.command.labTestRequest.delete;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteLabTestRequestCommand implements ICommand {
    private UUID id;

    @Override
    public ICommandMessage getMessage() {
        return new DeleteLabTestRequestMessage();
    }
}
