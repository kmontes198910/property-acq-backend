package com.kynsoft.rrhh.application.command.nurse.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class UpdateNurseCommand implements ICommand {
    private UUID id;
    private String identification;
    private String code;
    private String email;
    private String name;
    private String lastName;
    private String status;
    private String registerNumber;
    private String language;
    private boolean isExpress;
    private String clasificacion; // Campo añadido para almacenar la clasificación de la enfermera
    private String phoneNumber;
    private String image;

    public static UpdateNurseCommand fromRequest(UpdateNurseRequest request, UUID id) {
        UpdateNurseCommand command = new UpdateNurseCommand();
        command.setId(id);
        command.setIdentification(request.getIdentification());
        command.setCode(request.getCode());
        command.setEmail(request.getEmail());
        command.setName(request.getName());
        command.setLastName(request.getLastName());
        command.setStatus(request.getStatus());
        command.setRegisterNumber(request.getRegisterNumber());
        command.setLanguage(request.getLanguage());
        command.setExpress(request.isExpress());
        command.setClasificacion(request.getClasificacion());
        command.setPhoneNumber(request.getPhoneNumber());
        command.setImage(request.getImage());
        return command;
    }

    @Override
    public ICommandMessage getMessage() {
        return null;
    }
}