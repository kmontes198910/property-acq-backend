package com.kynsof.evaluation.application.command.evaluation.create;

import com.kynsof.evaluation.domain.dto.BusinessDto;
import com.kynsof.evaluation.domain.dto.DoctorDto;
import com.kynsof.evaluation.domain.dto.EvaluationDto;
import com.kynsof.evaluation.domain.dto.PatientDto;
import com.kynsof.evaluation.domain.service.IBusinessService;
import com.kynsof.evaluation.domain.service.IDoctorService;
import com.kynsof.evaluation.domain.service.IEvaluationService;
import com.kynsof.evaluation.domain.service.IPatientsService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class CreateEvaluationCommandHandler implements ICommandHandler<CreateEvaluationCommand> {

    private final IEvaluationService serviceImpl;
    private final IPatientsService patientsService;
    private final IBusinessService businessService;
    private final IDoctorService doctorService;

    public CreateEvaluationCommandHandler(IEvaluationService serviceImpl,
                                          IPatientsService patientsService,
                                          IDoctorService doctorService,
                                          IBusinessService businessService) {
        this.serviceImpl = serviceImpl;
        this.patientsService = patientsService;
        this.doctorService = doctorService;
        this.businessService = businessService;
    }

    @Override
    public void handle(CreateEvaluationCommand command) {
        PatientDto patientDto = this.patientsService.findById(command.getPatient());
        DoctorDto doctorDto = this.doctorService.findById(command.getDoctor());
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
