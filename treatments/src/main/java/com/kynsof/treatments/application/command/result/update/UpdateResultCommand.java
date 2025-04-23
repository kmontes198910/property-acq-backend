package com.kynsof.treatments.application.command.result.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateResultCommand implements ICommand {

    private final UUID id;
    private String type;
    private String url;
    private String uploadedById;
    private String uploadedByUsername;
    private UUID externalConsultationId;

    public UpdateResultCommand(UUID id, String type, String url, String uploadedById, 
                              String uploadedByUsername, UUID externalConsultationId) {
        this.id = id;
        this.type = type;
        this.url = url;
        this.uploadedById = uploadedById;
        this.uploadedByUsername = uploadedByUsername;
        this.externalConsultationId = externalConsultationId;
    }

    public static UpdateResultCommand fromRequest(UpdateResultRequest request) {
        return new UpdateResultCommand(
                request.getId(),
                request.getType(),
                request.getUrl(),
                request.getUploadedById(),
                request.getUploadedByUsername(),
                request.getExternalConsultationId()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateResultMessage(id);
    }
}