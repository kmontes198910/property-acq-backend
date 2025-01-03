package com.kynsof.treatments.application.command.optometryExam.update;

import com.kynsof.share.core.domain.RulesChecker;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.treatments.domain.dto.OptometryExamDto;
import com.kynsof.treatments.domain.rules.externalconsultation.ExternalConsultationCreateAtNotEqualsRule;
import com.kynsof.treatments.domain.service.IOptometryExamService;
import org.springframework.stereotype.Component;

@Component
public class UpdateOptometryExamCommandHandler implements ICommandHandler<UpdateOptometryExamCommand> {

    private final IOptometryExamService service;

    public UpdateOptometryExamCommandHandler(IOptometryExamService service) {
        this.service = service;
    }

    @Override
    public void handle(UpdateOptometryExamCommand command) {
        OptometryExamDto existingExam = service.findById(command.getId());
        existingExam.setSphereOd(command.getSphereOd());
        existingExam.setCylinderOd(command.getCylinderOd());
        existingExam.setAxisOd(command.getAxisOd());
        existingExam.setAvscOd(command.getAvscOd());
        existingExam.setAvccOd(command.getAvccOd());
        existingExam.setSphereOi(command.getSphereOi());
        existingExam.setCylinderOi(command.getCylinderOi());
        existingExam.setAxisOi(command.getAxisOi());
        existingExam.setAvscOi(command.getAvscOi());
        existingExam.setAvccOi(command.getAvccOi());
        existingExam.setAddPower(command.getAddPower());
        existingExam.setDp(command.getDp());
        existingExam.setDv(command.getDv());
        existingExam.setFilter(command.getFilter());
        existingExam.setCurrent(command.getIsCurrent());
        existingExam.setAvccAdd(command.getAvccAdd());
        existingExam.setSphereAdd(command.getSphereAdd());
        existingExam.setCylinderAdd(command.getCylinderAdd());
        existingExam.setAxisAdd(command.getAxisAdd());
        existingExam.setAvscAdd(command.getAvscAdd());
        service.update(existingExam);
    }
}