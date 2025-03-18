package com.kynsof.evaluation.application.command.patients.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class UpdatePatientsCommand implements ICommand {

    private UUID id;
    private String identification;
    private String name;
    private String lastName;
    private String gender;
    private LocalDate birthDate;
    private String profession;

    public UpdatePatientsCommand(UUID id, String identification, String name, String lastName, String gender,
            LocalDate birthDate, String profession) {
        this.id = id;
        this.identification = identification;
        this.name = name;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
        this.profession = profession;
    }

    public static UpdatePatientsCommand fromRequest(UUID id, UpdatePatientsRequest request) {
        return new UpdatePatientsCommand(id, request.getIdentification(), request.getName(), request.getLastName(),
                request.getGender(), request.getBirthDate(), request.getProfession());
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdatePatientMessage();
    }
}
