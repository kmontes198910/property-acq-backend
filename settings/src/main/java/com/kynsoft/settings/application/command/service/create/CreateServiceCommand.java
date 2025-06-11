package com.kynsoft.settings.application.command.service.create;


import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsoft.settings.domain.enums.EServiceStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateServiceCommand implements ICommand {

    private UUID id;
    private String image;
    private String name;
    private String description;
    private UUID type;
    private boolean applyIva;
    private EServiceStatus status;
    private Integer estimatedDuration;
    private String code;
    public CreateServiceCommand(String name, String picture, String description, UUID serviceTypeId,
                                Boolean applyIva, EServiceStatus status, Integer estimatedDuration, String code) {
        this.applyIva = applyIva;
        this.id = UUID.randomUUID();
        this.name = name;
        this.image = picture;
        this.description = description;
        this.type = serviceTypeId;
        this.status = status;
        this.estimatedDuration = estimatedDuration;
        this.code = code;
    }

    public static CreateServiceCommand fromRequest(CreateServiceRequest request) {
        return new CreateServiceCommand(request.getName(), request.getImage(), request.getDescription(),
                request.getType(),
                request.isApplyIva(),
                request.getStatus(), request.getEstimatedDuration(),
                request.getCode());
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateServiceMessage(id);
    }
}
