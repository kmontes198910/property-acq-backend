package com.kynsof.treatments.application.command.externalConsultation.update;

import com.kynsof.share.core.domain.RulesChecker;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.rules.ValidateObjectNotNullRule;
import com.kynsof.share.utils.UpdateIfNotNull;
import com.kynsof.treatments.domain.dto.*;
import com.kynsof.treatments.domain.dto.enumDto.Status;
import com.kynsof.treatments.domain.rules.externalconsultation.ExternalConsultationCreateAtNotEqualsRule;
import com.kynsof.treatments.domain.service.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class UpdateExternalConsultationAllCommandHandler implements ICommandHandler<UpdateExternalConsultationAllCommand> {

    private final IExternalConsultationService externalConsultationService;
    private final IMedicinesService medicinesService;
    private final ITreatmentService treatmentService;
    private final IDiagnosisService diagnosisService;
    private final IExamService examService;
    private final IOptometryExamService optometryExamService;

    public UpdateExternalConsultationAllCommandHandler(IExternalConsultationService externalConsultationService,
                                                       IMedicinesService medicinesService,
                                                       ITreatmentService treatmentService,
                                                       IDiagnosisService diagnosisService,
                                                       IExamService examService, IOptometryExamService optometryExamService) {
        this.externalConsultationService = externalConsultationService;
        this.medicinesService = medicinesService;
        this.treatmentService = treatmentService;
        this.diagnosisService = diagnosisService;
        this.examService = examService;
        this.optometryExamService = optometryExamService;
    }

    @Override
    public void handle(UpdateExternalConsultationAllCommand command) {
        RulesChecker.checkRule(new ValidateObjectNotNullRule<>(command.getId(), "id", "External Consultation ID cannot be null."));
        ExternalConsultationDto externalConsultationDto = externalConsultationService.findById(command.getId());

        RulesChecker.checkRule(new ExternalConsultationCreateAtNotEqualsRule(externalConsultationDto.getConsultationTime()));


        if (!command.getDiagnosis().isEmpty()) {
            if (!externalConsultationDto.getDiagnoses().isEmpty()) {
                List<UUID> diagnosesToDelete = externalConsultationDto.getDiagnoses().stream()
                        .map(DiagnosisDto::getId)
                        .collect(Collectors.toList());
                this.diagnosisService.deleteByIds(diagnosesToDelete);
            }
            List<DiagnosisDto> diagnosisDtoList = command.getDiagnosis().stream().map(diagnosisRequest
                    -> new DiagnosisDto(UUID.randomUUID(), diagnosisRequest.getIcdCode(), diagnosisRequest.getDescription())).toList();
            externalConsultationDto.setDiagnoses(diagnosisDtoList);
        }

        if (command.getTreatments() != null && !command.getTreatments().isEmpty()) {
            if (!externalConsultationDto.getTreatments().isEmpty()) {
                List<UUID> treatmentsToDelete = externalConsultationDto.getTreatments().stream()
                        .map(TreatmentDto::getId)
                        .collect(Collectors.toList());
                this.treatmentService.deleteByIds(treatmentsToDelete);
            }
            List<TreatmentDto> treatmentDtoList = command.getTreatments().stream().map(treatmentRequest -> {
                MedicinesDto medicinesDto = medicinesService.findById(treatmentRequest.getMedication());
                return new TreatmentDto(
                        UUID.randomUUID(),
                        treatmentRequest.getDescription(),
                        medicinesDto,
                        treatmentRequest.getQuantity(),
                        treatmentRequest.getMedicineUnit()
                );
            }).toList();
            externalConsultationDto.setTreatments(treatmentDtoList);
        }

        if (command.getOptometryExams() != null && !command.getOptometryExams().isEmpty()) {
            if (!externalConsultationDto.getOptometryExams().isEmpty()) {
                List<UUID> optometryExamsToDelete = externalConsultationDto.getOptometryExams().stream().map(OptometryExamDto::getId).toList();
                this.optometryExamService.deleteByIds(optometryExamsToDelete);
            }

            List<OptometryExamDto> optometryExamDtoList = command.getOptometryExams().stream()
                    .map(optometryExamRequest -> {
                        OptometryExamDto dto = new OptometryExamDto();
                        dto.setId(UUID.randomUUID());
                        dto.setSphereOd(optometryExamRequest.getSphereOd());
                        dto.setCylinderOd(optometryExamRequest.getCylinderOd());
                        dto.setAxisOd(optometryExamRequest.getAxisOd());
                        dto.setAvscOd(optometryExamRequest.getAvscOd());
                        dto.setAvccOd(optometryExamRequest.getAvccOd());
                        dto.setSphereOi(optometryExamRequest.getSphereOi());
                        dto.setCylinderOi(optometryExamRequest.getCylinderOi());
                        dto.setAxisOi(optometryExamRequest.getAxisOi());
                        dto.setAvscOi(optometryExamRequest.getAvscOi());
                        dto.setAvccOi(optometryExamRequest.getAvccOi());
                        dto.setAddPower(optometryExamRequest.getAddPower());
                        dto.setDp(optometryExamRequest.getDp());
                        dto.setDv(optometryExamRequest.getDv());
                        dto.setFilter(optometryExamRequest.getFilter());
                        dto.setCurrent(optometryExamRequest.isCurrent());
                        dto.setAvccAdd(optometryExamRequest.getAvccAdd());
                        dto.setSphereAdd(optometryExamRequest.getSphereAdd());
                        dto.setCylinderAdd(optometryExamRequest.getCylinderAdd());
                        dto.setAvscAdd(optometryExamRequest.getAvscAdd());
                        dto.setAxisAdd(optometryExamRequest.getAxisAdd());
                        return dto;
                    }).toList();
            externalConsultationDto.setOptometryExams(optometryExamDtoList);
        }

        if (command.getExamOrder() != null && !command.getExamOrder().getExams().isEmpty()) {
            if (externalConsultationDto.getExamOrder() != null && !externalConsultationDto.getExamOrder().getExams().isEmpty()) {
                List<UUID> examsToDelete = externalConsultationDto.getExamOrder().getExams().stream()
                        .map(ExamDto::getId)
                        .collect(Collectors.toList());
                this.examService.deleteByIds(examsToDelete);
            }

            List<ExamDto> examDtoList = command.getExamOrder().getExams().stream()
                    .map(examRequest -> new ExamDto(
                            UUID.randomUUID(),
                            examRequest.getName(),
                            examRequest.getDescription(),
                            examRequest.getType(),
                            "",
                            new Date(),
                            examRequest.getCode()
                    ))
                    .collect(Collectors.toList());

            ExamOrderDto examOrderDto = new ExamOrderDto(
                    UUID.randomUUID(),
                    command.getExamOrder().getReason(),
                    Status.ACTIVE.toString(),
                    new Date(),
                    externalConsultationDto.getPatient(),
                    examDtoList
            );
            externalConsultationDto.setExamOrder(examOrderDto);
        }

        UpdateIfNotNull.updateIfNotNull(externalConsultationDto::setConsultationReason, command.getConsultationReason());
        UpdateIfNotNull.updateIfNotNull(externalConsultationDto::setMedicalHistory, command.getMedicalHistory());
        UpdateIfNotNull.updateIfNotNull(externalConsultationDto::setPhysicalExam, command.getPhysicalExam());
        UpdateIfNotNull.updateIfNotNull(externalConsultationDto::setObservations, command.getObservations());
        UUID id = externalConsultationService.update(externalConsultationDto);
        command.setId(id);
    }
}
