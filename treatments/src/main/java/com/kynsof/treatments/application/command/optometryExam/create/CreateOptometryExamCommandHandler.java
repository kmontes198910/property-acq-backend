package com.kynsof.treatments.application.command.optometryExam.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.treatments.domain.dto.OptometryExamDto;
import com.kynsof.treatments.domain.service.IOptometryExamService;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CreateOptometryExamCommandHandler implements ICommandHandler<CreateOptometryExamCommand> {

    private final IOptometryExamService optometryExamService;

    public CreateOptometryExamCommandHandler(IOptometryExamService optometryExamService) {
        this.optometryExamService = optometryExamService;
    }

    @Override
    public void handle(CreateOptometryExamCommand command) {
        UUID id = UUID.randomUUID();
        OptometryExamDto examDto = new OptometryExamDto(
                id, command.getSphereOd(), command.getCylinderOd(), command.getAxisOd(), command.getAvscOd(),
                command.getAvccOd(), command.getSphereOi(), command.getCylinderOi(), command.getAxisOi(),
                command.getAvscOi(), command.getAvccOi(), command.getAddPower(), command.getDp(),
                command.getDv(), command.getFilter(), command.isCurrent(),command.getAvccAdd(),
                command.getSphereAdd(), command.getCylinderAdd(), command.getAvscAdd(), command.getAxisAdd(),
                1
        );

        examDto.setId(id);
     //   examDto.setExternalConsultationId(command.getExternalConsultationId());
        optometryExamService.create(examDto);

        command.setId(id);
    }
}