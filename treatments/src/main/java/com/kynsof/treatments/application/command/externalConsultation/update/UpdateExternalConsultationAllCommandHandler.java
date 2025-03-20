package com.kynsof.treatments.application.command.externalConsultation.update;

import com.kynsof.share.core.domain.RulesChecker;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.rules.ValidateObjectNotNullRule;
import com.kynsof.share.utils.UpdateIfNotNull;
import com.kynsof.treatments.application.command.externalConsultation.create.OptometryExamRequest;
import com.kynsof.treatments.domain.dto.ExternalConsultationDto;
import com.kynsof.treatments.domain.dto.OptometryExamDto;
import com.kynsof.treatments.domain.rules.externalconsultation.ExternalConsultationCreateAtNotEqualsRule;
import com.kynsof.treatments.domain.service.IExternalConsultationService;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class UpdateExternalConsultationAllCommandHandler implements ICommandHandler<UpdateExternalConsultationAllCommand> {

    private final IExternalConsultationService externalConsultationService;

    public UpdateExternalConsultationAllCommandHandler(IExternalConsultationService externalConsultationService) {
        this.externalConsultationService = externalConsultationService;
    }

    @Override
    public void handle(UpdateExternalConsultationAllCommand command) {
        // Validación de campos principales
        RulesChecker.checkRule(new ValidateObjectNotNullRule<>(command.getId(), "id", "External Consultation ID cannot be null."));
        ExternalConsultationDto externalConsultationDto = externalConsultationService.findById(command.getId());

      RulesChecker.checkRule(new ExternalConsultationCreateAtNotEqualsRule(externalConsultationDto.getConsultationTime()));
        if (command.getOptometryExamRequests() != null) {
            List<OptometryExamDto> newOptometryExams = command.getOptometryExamRequests().stream()
                    .filter(OptometryExamRequest::isCurrent)
                    .map(o -> {
                        OptometryExamDto dto = new OptometryExamDto();
                        dto.setId(UUID.randomUUID());
                        dto.setSphereOd(o.getSphereOd());
                        dto.setCylinderOd(o.getCylinderOd());
                        dto.setAxisOd(o.getAxisOd());
                        dto.setAvscOd(o.getAvscOd());
                        dto.setAvccOd(o.getAvccOd());
                        dto.setSphereOi(o.getSphereOi());
                        dto.setCylinderOi(o.getCylinderOi());
                        dto.setAxisOi(o.getAxisOi());
                        dto.setAvscOi(o.getAvscOi());
                        dto.setAvccOi(o.getAvccOi());
                        dto.setAddPower(o.getAddPower());
                        dto.setDp(o.getDp());
                        dto.setDv(o.getDv());
                        dto.setFilter(o.getFilter());
                        dto.setCurrent(o.isCurrent());
                        dto.setAvccAdd(o.getAvccAdd());
                        dto.setSphereAdd(o.getSphereAdd());
                        dto.setCylinderAdd(o.getCylinderAdd());
                        dto.setAvscAdd(o.getAvscAdd());
                        dto.setAxisAdd(o.getAxisAdd());
                        return dto;
                    }).toList();

            externalConsultationDto.setOptometryExams(newOptometryExams);
        }

        // Actualización de campos simples
        UpdateIfNotNull.updateIfNotNull(externalConsultationDto::setConsultationReason, command.getConsultationReason());
        UpdateIfNotNull.updateIfNotNull(externalConsultationDto::setMedicalHistory, command.getMedicalHistory());
        UpdateIfNotNull.updateIfNotNull(externalConsultationDto::setPhysicalExam, command.getPhysicalExam());
        UpdateIfNotNull.updateIfNotNull(externalConsultationDto::setObservations, command.getObservations());
        // UpdateIfNotNull.updateIfNotNull(externalConsultationDto::setMedicalSpeciality, command.getMedicalSpeciality());

        // Persistencia de la actualización
        externalConsultationDto.setOdontogramJson(command.getOdontogramJson());
        UUID id = externalConsultationService.update(externalConsultationDto);
        command.setId(id);
    }
}