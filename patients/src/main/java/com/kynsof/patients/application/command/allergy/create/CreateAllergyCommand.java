package com.kynsof.patients.application.command.allergy.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateAllergyCommand implements ICommand {
    private UUID id;
    private String code;
    private String name;
    private UUID medicalInformationId;


    public CreateAllergyCommand(UUID medicalInformationId, String code, String name) {
        this.code = code;
        this.name = name;
        this.medicalInformationId = medicalInformationId;
    }
    public static CreateAllergyCommand fromRequest(CreateAllergyEntityRequest request) {
        return new CreateAllergyCommand(request.getMedicalInformationId(), request.getCode(), request.getName());
    }


    @Override
    public ICommandMessage getMessage() {
        return new CreateAllergyMessage(id);
    }
}
