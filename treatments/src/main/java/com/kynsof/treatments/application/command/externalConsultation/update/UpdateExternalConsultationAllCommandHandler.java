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
import java.util.stream.Collectors;

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
//        // Manejo de Diagnosis por UUID
//        if (command.getDiagnosis() != null) {
//            List<UUID> incomingIds = command.getDiagnosis().stream()
//                    .filter(d -> d.getId() != null)
//                    .map(UpdateDiagnosisAllRequest::getId)
//                    .toList();
//
//            List<DiagnosisDto> updatedDiagnoses = externalConsultationDto.getDiagnoses().stream()
//                    .filter(d -> incomingIds.contains(d.getId())) // Mantener los existentes
//                    .collect(Collectors.toList());
//
//            List<DiagnosisDto> newDiagnoses = command.getDiagnosis().stream()
//                    .filter(d -> d.getId() == null) // Agregar nuevos
//                    .map(d -> new DiagnosisDto(UUID.randomUUID(), d.getIcdCode(), d.getDescription()))
//                    .toList();
//
//            updatedDiagnoses.addAll(newDiagnoses); // Combina los existentes con los nuevos
//            externalConsultationDto.setDiagnoses(updatedDiagnoses);
//        }
//
//        // Manejo de Treatments por UUID
//        if (command.getTreatments() != null) {
//            List<UUID> incomingIds = command.getTreatments().stream()
//                    .filter(t -> t.getId() != null)
//                    .map(UpdateTreatmentAllRequest::getMedication)
//                    .toList();
//
//            List<TreatmentDto> updatedTreatments = externalConsultationDto.getTreatments().stream()
//                    .filter(t -> incomingIds.contains(t.getId()))
//                    .collect(Collectors.toList());
//
//            List<TreatmentDto> newTreatments = command.getTreatments().stream()
//                    .filter(t -> t.getId() == null)
//                    .map(t -> {
//                        MedicinesDto medicinesDto = medicinesService.findById(t.getMedication());
//                        return new TreatmentDto(
//                                UUID.randomUUID(),
//                                t.getDescription(),
//                                medicinesDto,
//                                t.getQuantity(),
//                                t.getMedicineUnit());
//                    }).toList();
//
//            updatedTreatments.addAll(newTreatments);
//            externalConsultationDto.setTreatments(updatedTreatments);
//        }
//
//        // Manejo de Optometry Exams por UUID
//        if (command.getOptometryExams() != null) {
//            List<UUID> incomingIds = command.getOptometryExams().stream()
//                    .filter(o -> o.getId() != null)
//                    .map(UpdateOptometryExamRequest::getId)
//                    .toList();
//
//            List<OptometryExamDto> updatedOptometryExams = externalConsultationDto.getOptometryExams().stream()
//                    .filter(o -> incomingIds.contains(o.getId()))
//                    .collect(Collectors.toList());
//
//            List<OptometryExamDto> newOptometryExams = command.getOptometryExams().stream()
//                    .filter(o -> o.getId() == null)
//                    .map(o -> {
//                        OptometryExamDto dto = new OptometryExamDto();
//                        dto.setId(UUID.randomUUID());
//                        dto.setSphereOd(o.getSphereOd());
//                        dto.setCylinderOd(o.getCylinderOd());
//                        dto.setAxisOd(o.getAxisOd());
//                        dto.setAvscOd(o.getAvscOd());
//                        dto.setAvccOd(o.getAvccOd());
//                        dto.setSphereOi(o.getSphereOi());
//                        dto.setCylinderOi(o.getCylinderOi());
//                        dto.setAxisOi(o.getAxisOi());
//                        dto.setAvscOi(o.getAvscOi());
//                        dto.setAvccOi(o.getAvccOi());
//                        dto.setAddPower(o.getAddPower());
//                        dto.setDp(o.getDp());
//                        dto.setDv(o.getDv());
//                        dto.setFilter(o.getFilter());
//                        dto.setCurrent(o.isCurrent());
//                        dto.setAvccAdd(o.getAvccAdd());
//                        dto.setSphereAdd(o.getSphereAdd());
//                        dto.setCylinderAdd(o.getCylinderAdd());
//                        dto.setAvscAdd(o.getAvscAdd());
//                        dto.setAxisAdd(o.getAxisAdd());
//                        return dto;
//                    }).toList();
//
//            updatedOptometryExams.addAll(newOptometryExams);
//            externalConsultationDto.setOptometryExams(updatedOptometryExams);
//        }
//
//        // Manejo de ExamOrder y Exams por UUID
//        if (command.getExamOrder() != null) {
//            ExamOrderDto currentExamOrder = externalConsultationDto.getExamOrder();
//
//            List<UUID> incomingExamIds = command.getExamOrder().getExams().stream()
//                    .map(e -> e.getId())
//                    .filter(id -> id != null)
//                    .toList();
////
////            List<ExamDto> updatedExams = (currentExamOrder != null ? currentExamOrder.getExams() : List.of()).stream()
////                    .filter(e -> incomingExamIds.contains(e.getId()))
////                    .collect(Collectors.toList());
//
//            List<ExamDto> newExams = command.getExamOrder().getExams().stream()
//                    .filter(e -> e.getId() == null)
//                    .map(e -> new ExamDto(
//                            UUID.randomUUID(),
//                            e.getName(),
//                            e.getDescription(),
//                            e.getType(),
//                            "",
//                            new Date(),
//                            e.getCode()))
//                    .toList();
//
////            updatedExams.addAll(newExams);
////
////            ExamOrderDto updatedExamOrder = new ExamOrderDto(
////                    currentExamOrder != null ? currentExamOrder.getId() : UUID.randomUUID(),
////                    command.getExamOrder().getReason(),
////                    Status.ACTIVE.toString(),
////                    new Date(),
////                    externalConsultationDto.getPatient(),
////                    updatedExams
////            );
////            externalConsultationDto.setExamOrder(updatedExamOrder);
//        }

        // Actualización de campos simples
        UpdateIfNotNull.updateIfNotNull(externalConsultationDto::setConsultationReason, command.getConsultationReason());
        UpdateIfNotNull.updateIfNotNull(externalConsultationDto::setMedicalHistory, command.getMedicalHistory());
        UpdateIfNotNull.updateIfNotNull(externalConsultationDto::setPhysicalExam, command.getPhysicalExam());
        UpdateIfNotNull.updateIfNotNull(externalConsultationDto::setObservations, command.getObservations());
        // UpdateIfNotNull.updateIfNotNull(externalConsultationDto::setMedicalSpeciality, command.getMedicalSpeciality());

        // Persistencia de la actualización
        UUID id = externalConsultationService.update(externalConsultationDto);
        command.setId(id);
    }
}