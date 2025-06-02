package com.kynsoft.medicaltest.application.command.labtest.delete;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Comando para eliminar un examen de laboratorio
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeleteLabTestCommand implements ICommand {
    private UUID id;

    @Override
    public ICommandMessage getMessage() {
        return new DeleteLabTestMessage();
    }
}
