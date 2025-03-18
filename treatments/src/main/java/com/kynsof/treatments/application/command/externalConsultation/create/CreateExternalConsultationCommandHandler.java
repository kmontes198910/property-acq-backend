package com.kynsof.treatments.application.command.externalConsultation.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.treatments.domain.dto.*;
import com.kynsof.treatments.domain.evnts.CreateBillingEvent;
import com.kynsof.treatments.domain.service.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
public class CreateExternalConsultationCommandHandler implements ICommandHandler<CreateExternalConsultationCommand> {

    private final IExternalConsultationService externalConsultationService;
    private final IPatientsService patientsService;
    private final IDoctorService doctorService;
    private final IMedicinesService medicinesService;
    private final IBusiness businessService;
    private final IServiceService serviceService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final IBusinessBalanceService businessBalanceService;

    public CreateExternalConsultationCommandHandler(IExternalConsultationService externalConsultationService,
                                                    IPatientsService patientsService,
                                                    IDoctorService doctorService,
                                                    IMedicinesService medicinesService,
                                                    IBusiness businessService, IServiceService serviceService,
                                                    ApplicationEventPublisher applicationEventPublisher
            , IBusinessBalanceService businessBalanceService) {
        this.externalConsultationService = externalConsultationService;
        this.patientsService = patientsService;
        this.doctorService = doctorService;
        this.medicinesService = medicinesService;
        this.businessService = businessService;
        this.serviceService = serviceService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.businessBalanceService = businessBalanceService;
    }

    @Override
    public void handle(CreateExternalConsultationCommand command) {
        PatientDto patientDto = patientsService.findById(command.getPatientId());
        DoctorDto doctorDto = doctorService.findById(command.getDoctorId());
        BusinessDto businessDto = businessService.findById(command.getBusinessId());
        ServiceDto serviceDto = serviceService.findByIds(UUID.fromString(command.getMedicalSpeciality()));

        List<DiagnosisDto> diagnosisDtoList = command.getDiagnosis().stream().map(diagnosisRequest ->
                new DiagnosisDto(UUID.randomUUID(), diagnosisRequest.getIcdCode(), diagnosisRequest.getDescription())).toList();

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

        List<ExamDto> examDtoList = command.getExams() != null ? command.getExams().stream().map(examRequest -> new ExamDto(
                UUID.randomUUID(),
                examRequest.getName(),
                examRequest.getDescription(),
                examRequest.getType(),
                examRequest.getCode()
        )).toList() : new ArrayList<>();

        List<OptometryExamDto> optometryExamDtoList = new ArrayList<>();
        if (command.getOptometryExams() != null && !command.getOptometryExams().isEmpty()) {
            int[] counter = {1}; // Usar un array para manejar el contador dentro del stream
            optometryExamDtoList = command.getOptometryExams().stream()
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
                        dto.setOrderNumber(counter[0]++); // Asignar el valor del contador y luego incrementarlo
                        return dto;
                    }).toList();
        }

        UUID id = externalConsultationService.createAll(new ExternalConsultationDto(
                UUID.randomUUID(),
                patientDto,
                doctorDto,
                new Date(),
                command.getConsultationReason(),
                command.getMedicalHistory(),
                command.getPhysicalExam(),
                diagnosisDtoList,
                treatmentDtoList,
                command.getObservations(),
                examDtoList,
                businessDto,
                serviceDto.getName(),
                "",
                serviceDto,
                optometryExamDtoList,
                command.getOdontogramJson()
        ));
        command.setId(id);
        try {
            System.err.println("Entro");
            String resulDiscount = businessBalanceService.discountBusinessBalance(command.getBusinessId(), 0.25);
            System.err.println("resulDiscount:" + resulDiscount);
        }catch (Exception e) {
            System.err.println("Ocurio un error");
            System.err.println(e.getMessage());

        }


//        if (!examDtoList.isEmpty()) {
//            CreateBillingEvent createBillingEvent = new CreateBillingEvent(
//                    command.getPatientId(),
//                    command.getBusinessId(),
//                    examDtoList.stream().map(ExamDto::getCode).toList()
//            );
//            applicationEventPublisher.publishEvent(createBillingEvent);
//        }
    }

}
