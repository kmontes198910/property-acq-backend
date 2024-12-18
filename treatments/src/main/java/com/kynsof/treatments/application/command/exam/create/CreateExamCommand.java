package com.kynsof.treatments.application.command.exam.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import com.kynsof.treatments.domain.dto.enumDto.MedicalExamCategory;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateExamCommand implements ICommand {

    private UUID id;
    private String name;
    private String description;
    private MedicalExamCategory type;
    private String code;
    private final UUID externalConsultationId;
    public CreateExamCommand(String name, String description, MedicalExamCategory type,
                             String code, UUID externalConsultationId) {
        this.name = name;
        this.description = description;
        this.type = type;
        this.code = code;
        this.externalConsultationId = externalConsultationId;
    }

    public static CreateExamCommand fromRequest(CreateExamRequest request) {
        return new CreateExamCommand(
                request.getName(), 
                request.getDescription(), 
                request.getType(),
                request.getCode(),
                request.getExternalConsultationId()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateExamMessage(id);
    }
}
