package com.kynsof.evaluation.application.command.evaluationPatient.update;

import com.kynsof.evaluation.domain.dto.EvaluationDto;
import com.kynsof.evaluation.domain.service.IDoctorService;
import com.kynsof.evaluation.domain.service.IEvaluationService;
import com.kynsof.evaluation.domain.service.IPatientsService;
import com.kynsof.evaluation.infrastructure.service.http.DoctorHttpUUIDService;
import com.kynsof.evaluation.infrastructure.service.http.PatientHttpUUIDService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class UpdateEvaluationPatientCommandHandler implements ICommandHandler<UpdateEvaluationPatientCommand> {

    private final IEvaluationService serviceImpl;
    private final IPatientsService patientsService;
    private final IDoctorService doctorService;
    private final PatientHttpUUIDService patientHttpUUIDService;
    private final DoctorHttpUUIDService doctorHttpUUIDService;

    public UpdateEvaluationPatientCommandHandler(IEvaluationService serviceImpl,
                                                 IPatientsService patientsService,
                                                 PatientHttpUUIDService patientHttpUUIDService,
                                                 IDoctorService doctorService,
                                                 DoctorHttpUUIDService doctorHttpUUIDService) {
        this.serviceImpl = serviceImpl;
        this.patientsService = patientsService;
        this.patientHttpUUIDService = patientHttpUUIDService;
        this.doctorService = doctorService;
        this.doctorHttpUUIDService = doctorHttpUUIDService;
    }

    @Override
    public void handle(UpdateEvaluationPatientCommand command) {
        EvaluationDto evaluationDto = this.serviceImpl.findByIds(command.getId());
        evaluationDto.setConsultationReason(command.getConsultationReason());
        evaluationDto.setPhysicalExam(command.getPhysicalExam());
        evaluationDto.setMedicalHistory(command.getMedicalHistory());
        evaluationDto.setObservation(command.getObservation());
        serviceImpl.update(evaluationDto);
    }
}
