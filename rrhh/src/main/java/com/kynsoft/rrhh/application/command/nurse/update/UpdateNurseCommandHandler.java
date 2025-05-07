package com.kynsoft.rrhh.application.command.nurse.update;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsoft.rrhh.domain.dto.NurseDto;
import com.kynsoft.rrhh.domain.interfaces.services.INurseService;
import com.kynsoft.rrhh.infrastructure.services.rabbitMQ.Dto.NurseRabbitMqDto;
import com.kynsoft.rrhh.infrastructure.services.rabbitMQ.eventPublisher.EventNursePublisherService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UpdateNurseCommandHandler implements ICommandHandler<UpdateNurseCommand> {

    private final INurseService service;
    private final EventNursePublisherService eventNursePublisherService;

    public UpdateNurseCommandHandler(INurseService service,
                                    EventNursePublisherService eventNursePublisherService) {
        this.service = service;
        this.eventNursePublisherService = eventNursePublisherService;
    }

    @Override
    @Transactional
    public void handle(UpdateNurseCommand command) {
        NurseDto nurseDto = service.findById(command.getId());
        
        // Actualizar solo los campos que vienen en el comando
        if (command.getName() != null) nurseDto.setName(command.getName());
        if (command.getLastName() != null) nurseDto.setLastName(command.getLastName());
        if (command.getStatus() != null) nurseDto.setStatus(command.getStatus());
        if (command.getIdentification() != null) nurseDto.setIdentification(command.getIdentification());
        if (command.getCode() != null) nurseDto.setCode(command.getCode());
        if (command.getEmail() != null) nurseDto.setEmail(command.getEmail());
        if (command.getRegisterNumber() != null) nurseDto.setRegisterNumber(command.getRegisterNumber());
        if (command.getLanguage() != null) nurseDto.setLanguage(command.getLanguage());
        nurseDto.setExpress(command.isExpress());
        if (command.getPhoneNumber() != null) nurseDto.setPhoneNumber(command.getPhoneNumber());
        if (command.getImage() != null) nurseDto.setImage(command.getImage());
        if (command.getClasificacion() != null) nurseDto.setClasificacion(command.getClasificacion());

        service.update(nurseDto);

        // Publicar evento de actualización
        this.eventNursePublisherService.publishEvent(new NurseRabbitMqDto(
                nurseDto.getId(), 
                nurseDto.getIdentification(), 
                nurseDto.getName(), 
                nurseDto.getLastName(), 
                nurseDto.getRegisterNumber(), 
                nurseDto.getStatus(), 
                nurseDto.getImage()
        ));
    }
}