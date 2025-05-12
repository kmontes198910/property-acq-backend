package com.kynsoft.cirugia.application.command.admision.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Command for updating an admission
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateAdmisionCommand implements ICommand {
    private UUID id;
    private UUID roomId;
    private UUID bedId;
    private String observations;
    private UUID updatedBy;

    @Override
    public ICommandMessage getMessage() {
        return new UpdateAdmisionMessage(this.id);
    }
}
