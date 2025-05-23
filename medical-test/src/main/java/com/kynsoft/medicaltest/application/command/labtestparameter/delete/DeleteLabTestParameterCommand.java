package com.kynsoft.medicaltest.application.command.labtestparameter.delete;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

/**
 * Comando para eliminar un parámetro de examen de laboratorio
 */
@Getter
@AllArgsConstructor
public class DeleteLabTestParameterCommand implements ICommand {
    
    private final UUID id;

    @Override
    public ICommandMessage getMessage() {
        return new DeleteLabTestParameterMessage(id);
    }
}
