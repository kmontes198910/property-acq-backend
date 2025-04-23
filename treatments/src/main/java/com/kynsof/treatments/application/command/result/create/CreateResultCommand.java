package com.kynsof.treatments.application.command.result.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateResultCommand implements ICommand {

    private UUID id;
    private String type;
    private String url;
    private String uploadedById;
    private String uploadedByUsername;
    private final UUID externalConsultationId;

    public CreateResultCommand(String type, String url, String uploadedById,
                               String uploadedByUsername, UUID externalConsultationId) {
        this.type = type;
        this.url = url;
        this.uploadedById = uploadedById;
        this.uploadedByUsername = uploadedByUsername;
        this.externalConsultationId = externalConsultationId;
    }

    public static CreateResultCommand fromRequest(CreateResultRequest request, String userId, String username) {
        return new CreateResultCommand(
                request.getType(),
                request.getUrl(),
                userId,
                username,
                request.getExternalConsultationId()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateResultMessage(id);
    }
}