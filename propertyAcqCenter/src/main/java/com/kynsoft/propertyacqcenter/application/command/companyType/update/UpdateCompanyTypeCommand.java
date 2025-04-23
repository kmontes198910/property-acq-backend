package com.kynsoft.propertyacqcenter.application.command.companyType.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateCompanyTypeCommand implements ICommand {

    private UUID id;
    private String name;
    private String code;
    private String description;
    private String examples;
    private Boolean isActive;

    public UpdateCompanyTypeCommand(UUID id, String name, String code, String description, 
                                 String examples, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.description = description;
        this.examples = examples;
        this.isActive = isActive;
    }

    public static UpdateCompanyTypeCommand fromRequest(UpdateCompanyTypeRequest request, UUID id) {
        return new UpdateCompanyTypeCommand(
                id,
                request.getName(),
                request.getCode(),
                request.getDescription(),
                request.getExamples(),
                request.getIsActive()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateCompanyTypeMessage(id);
    }
}
