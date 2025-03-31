package com.kynsof.hospitalizationService.application.response;

import com.kynsof.hospitalizationService.domain.dto.*;
import com.kynsof.share.core.domain.bus.query.IResponse;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HospitalizationResponse implements IResponse {
    private UUID id;
    private PatientDto patient;
    private EmergencyCaseDto emergencyCase;
    private MedicalStaffDto attendingDoctor;
    private LocalDate admissionDate;
    private String assignedRoom;
    private String hospitalizationStatus;

    public HospitalizationResponse(HospitalizationDto dto) {
        this.id = dto.getId();
        this.patient = dto.getPatient();
        this.emergencyCase = dto.getEmergencyCase();
        this.attendingDoctor = dto.getAttendingDoctor();
        this.admissionDate = dto.getAdmissionDate();
        this.assignedRoom = dto.getAssignedRoom();
        this.hospitalizationStatus = dto.getHospitalizationStatus();
    }

}
