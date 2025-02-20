package com.kynsof.patients.application.command.patients.patientsKeyCloack;

import com.kynsof.patients.application.command.patients.create.request.CreatePatientContactInfoRequest;
import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class patientsKeyCloakCommand implements ICommand {

    private final UUID id;
    private final  UUID keyCloackId;

    private CreatePatientContactInfoRequest createContactInfoRequest;


    public patientsKeyCloakCommand(UUID id, UUID keyCloackId) {

        this.id = id;
        this.keyCloackId = keyCloackId;
    }



    @Override
    public ICommandMessage getMessage() {
        return new patientsKeyCloakMessage();
    }
}
