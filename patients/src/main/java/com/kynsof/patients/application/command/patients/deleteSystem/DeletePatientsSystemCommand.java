package com.kynsof.patients.application.command.patients.deleteSystem;

import com.kynsof.patients.application.command.patients.delete.PatientDeleteMessage;
import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Getter
@Setter
@AllArgsConstructor
public class DeletePatientsSystemCommand implements ICommand {

    private UUID id;

    @Override
    public ICommandMessage getMessage() {
        return new PatientDeleteSystemMessage(id);
    }

}
