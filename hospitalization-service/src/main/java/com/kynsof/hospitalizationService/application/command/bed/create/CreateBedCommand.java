package com.kynsof.hospitalizationService.application.command.bed.create;

import com.kynsof.hospitalizationService.domain.dto.enun.BedStatus;
import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateBedCommand implements ICommand {

    private UUID id;
    private UUID ubication;
    private String code;
    private String name;
    private BedStatus status;

    public CreateBedCommand(UUID ubication, String code, String name, BedStatus status) {
        this.id = UUID.randomUUID();
        this.ubication = ubication;
        this.code = code;
        this.name = name;
        this.status = status;
    }

    public static CreateBedCommand fromRequest(CreateBedRequest request) {
        return new CreateBedCommand(
                request.getUbication(),
                request.getCode(),
                request.getName(),
                request.getStatus()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateBedMessage(id);
    }
}
