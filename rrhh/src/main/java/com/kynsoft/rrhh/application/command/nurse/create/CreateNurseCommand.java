package com.kynsoft.rrhh.application.command.nurse.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateNurseCommand implements ICommand {
    private UUID id;
    private String identification;
    private String code;
    private String email;
    private String name;
    private String lastName;
    private String registerNumber;
    private String language;
    private boolean isExpress;
    private String clasificacion; // Para almacenar: "licenciada", "tecnica", u otras clasificaciones
    private String phoneNumber;
    private String image;
    private UUID business;

    public static CreateNurseCommand fromRequest(CreateNurseRequest request) {
        CreateNurseCommand command = new CreateNurseCommand();
        command.setIdentification(request.getIdentification());
        command.setCode(request.getCode());
        command.setEmail(request.getEmail());
        command.setName(request.getName());
        command.setLastName(request.getLastName());
        command.setRegisterNumber(request.getRegisterNumber());
        command.setLanguage(request.getLanguage());
        command.setExpress(request.isExpress());
        command.setClasificacion(request.getClasificacion());
        command.setPhoneNumber(request.getPhoneNumber());
        command.setImage(request.getImage());
        command.setBusiness(request.getBusiness());
        return command;
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateNurseMessage(id);
    }
}