package com.kynsof.hospitalizationService.application.command.hospitalization.create;

import com.kynsof.hospitalizationService.domain.dto.EmergencyCaseDto;
import com.kynsof.hospitalizationService.domain.dto.HospitalizationDto;
import com.kynsof.hospitalizationService.domain.dto.MedicalStaffDto;
import com.kynsof.hospitalizationService.domain.dto.PatientDto;
import com.kynsof.hospitalizationService.domain.service.IEmergencyCaseService;
import com.kynsof.hospitalizationService.domain.service.IHospitalizationService;
import com.kynsof.hospitalizationService.domain.service.IMedicalStaffService;
import com.kynsof.hospitalizationService.domain.service.IPatientsService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import java.time.LocalDate;
import org.springframework.stereotype.Component;

@Component
public class CreateHospitalizationCommandHandler implements ICommandHandler<CreateHospitalizationCommand> {

    private final IEmergencyCaseService emergencyCaseService;
    private final IPatientsService patientsService;
    private final IMedicalStaffService medicalStaffService;
    private final IHospitalizationService hospitalizationService;

    public CreateHospitalizationCommandHandler(IEmergencyCaseService emergencyCaseService,
                                               IPatientsService patientsService,
                                               IMedicalStaffService medicalStaffService,
                                               IHospitalizationService hospitalizationService) {
        this.emergencyCaseService = emergencyCaseService;
        this.patientsService = patientsService;
        this.medicalStaffService = medicalStaffService;
        this.hospitalizationService = hospitalizationService;
    }

    @Override
    public void handle(CreateHospitalizationCommand command) {
        EmergencyCaseDto emergencyCaseDto = this.emergencyCaseService.findById(command.getEmergencyCase());
        PatientDto patientDto = this.patientsService.findById(command.getPatient());
        MedicalStaffDto medicalStaffDto = this.medicalStaffService.findById(command.getAttendingDoctor());
        this.hospitalizationService.create(new HospitalizationDto(
                command.getId(), 
                patientDto, 
                emergencyCaseDto, 
                medicalStaffDto, 
                LocalDate.parse(command.getAdmissionDate()), 
                command.getAssignedRoom(), 
                command.getHospitalizationStatus()
        ));
    }
}
