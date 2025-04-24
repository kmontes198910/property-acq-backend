package com.kynsoft.propertyacqcenter.application.command.constructionType.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateConstructionTypeCommand implements ICommand {

    private UUID id;
    private String name;
    private String description;
    private String code;
    private Boolean isSpecialized;
    private String specializationArea;
    private Boolean requiresLicense;
    private Boolean isActive;

    public UpdateConstructionTypeCommand(UUID id, String name, String description, String code, 
                                         Boolean isSpecialized, String specializationArea, 
                                         Boolean requiresLicense, Boolean isActive) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.code = code;
        this.isSpecialized = isSpecialized;
        this.specializationArea = specializationArea;
        this.requiresLicense = requiresLicense;
        this.isActive = isActive;
    }

    public static UpdateConstructionTypeCommand fromRequest(UpdateConstructionTypeRequest request, UUID id) {
        return new UpdateConstructionTypeCommand(
                id,
                request.getName(),
                request.getDescription(),
                request.getCode(),
                request.getIsSpecialized(),
                request.getSpecializationArea(),
                request.getRequiresLicense(),
                request.getIsActive()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateConstructionTypeMessage(id);
    }
}
