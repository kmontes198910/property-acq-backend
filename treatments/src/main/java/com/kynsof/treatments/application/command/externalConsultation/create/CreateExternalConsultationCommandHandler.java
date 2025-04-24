package com.kynsof.treatments.application.command.externalConsultation.create;

import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.treatments.domain.dto.*;
import com.kynsof.treatments.domain.service.*;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public CreateExternalConsultationCommandHandler(
            IExternalConsultationService externalConsultationService,
            IPatientsService patientsService,
            IDoctorService doctorService,
            IMedicinesService medicinesService,
            IBusiness businessService,
            IServiceService serviceService,
            ApplicationEventPublisher applicationEventPublisher,
            IBusinessBalanceService businessBalanceService) {
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
        // Obtener datos base
        PatientDto patientDto = patientsService.findById(command.getPatientId());
        DoctorDto doctorDto = doctorService.findById(command.getDoctorId());
        BusinessDto businessDto = businessService.findById(command.getBusinessId());
        ServiceDto serviceDto = serviceService.findByIds(UUID.fromString(command.getMedicalSpeciality()));

        // Mapear diagnósticos
        List<DiagnosisDto> diagnosisDtoList = command.getDiagnosis().stream()
                .map(diagnosis -> new DiagnosisDto(
                        UUID.randomUUID(),
                        diagnosis.getIcdCode(),
                        diagnosis.getDescription()))
                .collect(Collectors.toList());

        // Mapear tratamientos
        List<TreatmentDto> treatmentDtoList = command.getTreatments().stream()
                .map(treatment -> {
                    MedicinesDto medicinesDto = medicinesService.findById(treatment.getMedication());
                    return new TreatmentDto(
                            UUID.randomUUID(),
                            treatment.getDescription(),
                            medicinesDto,
                            treatment.getQuantity(),
                            treatment.getMedicineUnit()
                    );
                }).collect(Collectors.toList());

        // Mapear exámenes
        List<ExamDto> examDtoList = (command.getExams() != null) ? command.getExams().stream()
                .map(exam -> new ExamDto(
                        UUID.randomUUID(),
                        exam.getName(),
                        exam.getDescription(),
                        exam.getType(),
                        exam.getCode()))
                .collect(Collectors.toList()) : new ArrayList<>();

        // Mapear exámenes de optometría
        List<OptometryExamDto> optometryExamDtoList = mapOptometryExams(command);

        // Crear la consulta externa
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

        // Aplicar descuento en el balance del negocio
        applyBusinessBalanceDiscount(command);
    }

    /**
     * Mapea la lista de exámenes de optometría con un contador de orden.
     */
    private List<OptometryExamDto> mapOptometryExams(CreateExternalConsultationCommand command) {
        if (command.getOptometryExams() == null || command.getOptometryExams().isEmpty()) {
            return new ArrayList<>();
        }
        int[] counter = {1}; // Usar un array para manejar el contador dentro del stream

        return command.getOptometryExams().stream()
                .map(optometryExam -> {
                    OptometryExamDto dto = new OptometryExamDto();
                    dto.setId(UUID.randomUUID());
                    dto.setSphereOd(optometryExam.getSphereOd());
                    dto.setCylinderOd(optometryExam.getCylinderOd());
                    dto.setAxisOd(optometryExam.getAxisOd());
                    dto.setAvscOd(optometryExam.getAvscOd());
                    dto.setAvccOd(optometryExam.getAvccOd());
                    dto.setSphereOi(optometryExam.getSphereOi());
                    dto.setCylinderOi(optometryExam.getCylinderOi());
                    dto.setAxisOi(optometryExam.getAxisOi());
                    dto.setAvscOi(optometryExam.getAvscOi());
                    dto.setAvccOi(optometryExam.getAvccOi());
                    dto.setAddPower(optometryExam.getAddPower());
                    dto.setDp(optometryExam.getDp());
                    dto.setDv(optometryExam.getDv());
                    dto.setFilter(optometryExam.getFilter());
                    dto.setCurrent(optometryExam.isCurrent());
                    dto.setAvccAdd(optometryExam.getAvccAdd());
                    dto.setSphereAdd(optometryExam.getSphereAdd());
                    dto.setCylinderAdd(optometryExam.getCylinderAdd());
                    dto.setAvscAdd(optometryExam.getAvscAdd());
                    dto.setAxisAdd(optometryExam.getAxisAdd());
                    dto.setOrderNumber(counter[0]++); // Asignar el valor del contador y luego incrementarlo
                    return dto;
                })
                .collect(Collectors.toList());
    }

    /**
     * Aplica un descuento en el balance del negocio y maneja errores.
     */
    private void applyBusinessBalanceDiscount(CreateExternalConsultationCommand command) {
        try {
            System.err.println("Aplicando descuento al balance del negocio...");
            String resultDiscount = businessBalanceService.discountBusinessBalance(command.getBusinessId(), 0.25);
            System.err.println("Resultado del descuento: " + resultDiscount);
        } catch (Exception e) {
            System.err.println("Error al aplicar descuento en balance de negocio.");
            e.printStackTrace();
        }
    }
}