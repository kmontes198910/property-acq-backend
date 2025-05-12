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
    private  UUID room;
    private  String bed;
    private  String observations;
    private  UUID createdBy;

    public CreateAdmisionCommand(UUID surgeryId, UUID room, String bed, String observations, UUID createdBy) {
        this.surgeryId = surgeryId;
        this.room = room;
        this.bed = bed;
        this.observations = observations;
        this.createdBy = createdBy;
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateAdmisionMessage(this.id);
    }
}
