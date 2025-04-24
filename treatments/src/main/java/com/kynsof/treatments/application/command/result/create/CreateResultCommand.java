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
    private String base64Content;
    private String fileName;
    private String url;
    private String uploadedById;
    private String uploadedByUsername;
    private final UUID externalConsultationId;

    public CreateResultCommand(String type, String base64Content, String fileName, String uploadedById,
                               String uploadedByUsername, UUID externalConsultationId) {
        this.type = type;
        this.base64Content = base64Content;
        this.fileName = fileName;
        this.uploadedById = uploadedById;
        this.uploadedByUsername = uploadedByUsername;
        this.externalConsultationId = externalConsultationId;
    }

    public static CreateResultCommand fromRequest(CreateResultRequest request, String userId, String username) {
        return new CreateResultCommand(
                request.getType(),
                request.getBase64Content(),
                request.getFileName(),
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