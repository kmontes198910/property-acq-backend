package com.kynsof.hospitalizationService.application.command.hospitalization.update;

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
public class UpdateHospitalizationCommandHandler implements ICommandHandler<UpdateHospitalizationCommand> {

    private final IEmergencyCaseService emergencyCaseService;
    private final IPatientsService patientsService;
    private final IMedicalStaffService medicalStaffService;
    private final IHospitalizationService hospitalizationService;

    public UpdateHospitalizationCommandHandler(IEmergencyCaseService emergencyCaseService,
                                               IPatientsService patientsService,
                                               IMedicalStaffService medicalStaffService,
                                               IHospitalizationService hospitalizationService) {
        this.emergencyCaseService = emergencyCaseService;
        this.patientsService = patientsService;
        this.medicalStaffService = medicalStaffService;
        this.hospitalizationService = hospitalizationService;
    }

    @Override
    public void handle(UpdateHospitalizationCommand command) {
        EmergencyCaseDto emergencyCaseDto = this.emergencyCaseService.findById(command.getEmergencyCase());
        PatientDto patientDto = this.patientsService.findById(command.getPatient());
        MedicalStaffDto medicalStaffDto = this.medicalStaffService.findById(command.getAttendingDoctor());
        HospitalizationDto hospitalizationDto = this.hospitalizationService.findById(command.getId());

        hospitalizationDto.setPatient(patientDto);
        hospitalizationDto.setAttendingDoctor(medicalStaffDto);
        hospitalizationDto.setEmergencyCase(emergencyCaseDto);
        hospitalizationDto.setAdmissionDate(LocalDate.parse(command.getAdmissionDate()));
        hospitalizationDto.setAssignedRoom(command.getAssignedRoom());
        hospitalizationDto.setHospitalizationStatus(command.getHospitalizationStatus());

        this.hospitalizationService.update(hospitalizationDto);
    }
}
