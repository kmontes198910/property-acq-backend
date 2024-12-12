package com.kynsof.treatments.application.command.medicine.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateMedicineCommand implements ICommand {

    private UUID id;
    private String name;
    private final String presentation;

    public UpdateMedicineCommand(String name, UUID id, String presentation) {
        this.id = id;
        this.name = name;
        this.presentation = presentation;
    }

    public static UpdateMedicineCommand fromRequest(UpdateMedicineRequest request, UUID id) {
        return new UpdateMedicineCommand(
                request.getName(),
                id,
                request.getPresentation()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateMedicineMessage(id);
    }
}
