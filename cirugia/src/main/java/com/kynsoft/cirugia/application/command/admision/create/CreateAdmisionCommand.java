package com.kynsoft.cirugia.application.command.admision.create;


import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Command for creating an admission
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAdmisionCommand implements ICommand {
    private UUID id;
    private  UUID surgeryId;
    private  UUID roomId;
    private  UUID bedId;
    private  String observations;
    private  UUID createdBy;

    public CreateAdmisionCommand(UUID surgeryId, UUID room, UUID bed, String observations, UUID createdBy) {
        this.surgeryId = surgeryId;
        this.roomId = room;
        this.bedId = bed;
        this.observations = observations;
        this.createdBy = createdBy;
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateAdmisionMessage(this.id);
    }
}
