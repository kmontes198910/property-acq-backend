package com.kynsoft.settings.application.command.service.update;


import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.settings.domain.enums.EServiceStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateServiceCommand implements ICommand {

    private UUID id;
    private UUID serviceTypeId;
    private String picture;
    private String name;
    private String description;
    private boolean applyIva;
    private EServiceStatus status;
    private Integer estimatedDuration;



    public UpdateServiceCommand(UUID id, UUID serviceTypeId, String picture, String name,
                                String description, boolean applyIva,
                                EServiceStatus status, Integer estimatedDuration) {
        this.id = id;
        this.serviceTypeId = serviceTypeId;
        this.picture = picture;
        this.name = name;
        this.description = description;
        this.applyIva = applyIva;
        this.status = status;
        this.estimatedDuration = estimatedDuration;
    }


    public static UpdateServiceCommand fromRequest(UUID id, UpdateServiceRequest request) {
        return new UpdateServiceCommand(id, request.getType(), request.getImage(),
                request.getName(),
                request.getDescription(), request.isApplyIva(), request.getStatus(), request.getEstimatedDuration());
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateServiceMessage(id);
    }
}
