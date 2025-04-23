package com.kynsoft.propertyacqcenter.application.command.companyType.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateCompanyTypeCommand implements ICommand {

    private UUID id;
    private String name;
    private String code;
    private String description;
    private String examples;
    private Boolean isActive;

    public CreateCompanyTypeCommand(String name, String code, String description, String examples, Boolean isActive) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.code = code;
        this.description = description;
        this.examples = examples;
        this.isActive = isActive;
    }

    public static CreateCompanyTypeCommand fromRequest(CreateCompanyTypeRequest request) {
        return new CreateCompanyTypeCommand(
                request.getName(),
                request.getCode(),
                request.getDescription(),
                request.getExamples(),
                request.getIsActive()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateCompanyTypeMessage(id);
    }
}
