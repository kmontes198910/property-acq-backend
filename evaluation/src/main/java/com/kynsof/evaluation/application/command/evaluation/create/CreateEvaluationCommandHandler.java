package com.kynsof.evaluation.application.command.evaluation.create;

import com.kynsof.evaluation.domain.dto.BusinessDto;
import com.kynsof.evaluation.domain.dto.DoctorDto;
import com.kynsof.evaluation.domain.dto.EvaluationDto;
import com.kynsof.evaluation.domain.dto.PatientDto;
import com.kynsof.evaluation.domain.dto.enumDto.Status;
import com.kynsof.evaluation.domain.service.IBusinessService;
import com.kynsof.evaluation.domain.service.IDoctorService;
import com.kynsof.evaluation.domain.service.IEvaluationService;
import com.kynsof.evaluation.domain.service.IPatientsService;
import com.kynsof.evaluation.infrastructure.service.http.DoctorHttpUUIDService;
import com.kynsof.evaluation.infrastructure.service.http.PatientHttpUUIDService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.http.entity.DoctorHttp;
import com.kynsof.share.core.domain.http.entity.PatientHttp;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class CreateEvaluationCommandHandler implements ICommandHandler<CreateEvaluationCommand> {

    private final IEvaluationService serviceImpl;
    private final IPatientsService patientsService;
    private final IBusinessService businessService;
    private final IDoctorService doctorService;
    private final PatientHttpUUIDService patientHttpUUIDService;
    private final DoctorHttpUUIDService doctorHttpUUIDService;

    public CreateEvaluationCommandHandler(IEvaluationService serviceImpl,
                                          IPatientsService patientsService,
                                          PatientHttpUUIDService patientHttpUUIDService,
                                          IDoctorService doctorService,
                                          DoctorHttpUUIDService doctorHttpUUIDService,
                                          IBusinessService businessService) {
        this.serviceImpl = serviceImpl;
        this.patientsService = patientsService;
        this.patientHttpUUIDService = patientHttpUUIDService;
        this.doctorService = doctorService;
        this.doctorHttpUUIDService = doctorHttpUUIDService;
        this.businessService = businessService;
    }

    @Override
    public void handle(CreateEvaluationCommand command) {
        PatientDto patientDto = null;
        try {
            patientDto = this.patientsService.findById(command.getPatient());
        } catch (Exception e) {
            PatientHttp patient = patientHttpUUIDService.sendGetHttpRequest(command.getPatient());
            patientDto = new PatientDto(
                    patient.getId(),
                    patient.getIdentification(),
                    patient.getName(),
                    patient.getLastName(),
                    Status.valueOf(patient.getStatus()),
                    !patient.getBirthDate().equals("") ? LocalDate.parse(patient.getBirthDate()) : null,
                    patient.getProfession()
            );
            this.patientsService.create(patientDto);
        }
        DoctorDto doctorDto = null;
        try {
            doctorDto = this.doctorService.findById(command.getDoctor());
        } catch (Exception e) {
            DoctorHttp doctorHttp = this.doctorHttpUUIDService.sendGetHttpRequest(command.getDoctor());
            doctorDto = new DoctorDto(
                    doctorHttp.getId(), 
                    doctorHttp.getIdentification(), 
                    doctorHttp.getName(), 
                    doctorHttp.getLastName(), 
                    doctorHttp.getRegisterNumber(), 
                    doctorHttp.getImage(), 
                    Status.valueOf(doctorHttp.getStatus())
            );
            this.doctorService.create(doctorDto);
        }

        BusinessDto business = this.businessService.findById(command.getBusiness());
        serviceImpl.create(new EvaluationDto(
                command.getId(),
                patientDto,
                command.getConsultationReason(),
                command.getMedicalHistory(),
                command.getPhysicalExam(),
                command.getObservation(),
                doctorDto,
                business
        ));
    }
}
