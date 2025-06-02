//package com.kynsof.patients.application.command.patients.updatePatientAdmin;
//
//import com.kynsof.patients.domain.dto.PatientDto;
//import com.kynsof.patients.domain.service.IPatientsService;
//import com.kynsof.share.core.domain.bus.command.ICommandHandler;
//import org.springframework.stereotype.Component;
//
//@Component
//public class CreatePatientsAdminCommandHandler implements ICommandHandler<CreatePatientAdminCommand> {
//
//    private final IPatientsService serviceImpl;
//
//    public CreatePatientsAdminCommandHandler(IPatientsService serviceImpl
//    ) {
//        this.serviceImpl = serviceImpl;
//    }
//
//    @Override
//    public void handle(CreatePatientAdminCommand command) {
//        PatientDto patientDto = serviceImpl.findByIdSimple(command.getId());
//        patientDto.setPhoto(command.getPhoto());
//        patientDto.setGender(command.getGender());
//        patientDto.setDisabilityType(command.getDisabilityType());
//        patientDto.setIdentification(command.getIdentification());
//        patientDto.setGestationTime(command.getGestationTime());
//        patientDto.setHasDisability(command.getHasDisability());
//        patientDto.setIsPregnant(command.getIsPregnant());
//        serviceImpl.update(patientDto);
//
//    }
//}
