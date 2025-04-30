package com.kynsoft.cirugia.application.command.surgery.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class UpdateSurgeryCommand implements ICommand {

    private UUID id;
    private UUID surgeryId;
    private UUID patientId;
    private UUID doctorId;
    private UUID specialtyId;
    private String surgeryType;
    private String description;
    private LocalDateTime scheduledDate;
    private Integer estimatedDurationMinutes;
    private String complexityLevel;
    private UUID roomId;
    private Boolean requiresHospitalization;
    private String admissionReason;
    private String currentIllnessHistory;
    private String physicalExamination;
    private UUID updatedBy;

    public UpdateSurgeryCommand(UUID surgeryId, UUID patientId, UUID doctorId, UUID specialtyId, String surgeryType,
                             String description, LocalDateTime scheduledDate, Integer estimatedDurationMinutes,
                             String complexityLevel, UUID roomId, Boolean requiresHospitalization,
                             String admissionReason, String currentIllnessHistory,
                             String physicalExamination, UUID updatedBy) {
        this.id = UUID.randomUUID();
        this.surgeryId = surgeryId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.specialtyId = specialtyId;
        this.surgeryType = surgeryType;
        this.description = description;
        this.scheduledDate = scheduledDate;
        this.estimatedDurationMinutes = estimatedDurationMinutes;
        this.complexityLevel = complexityLevel;
        this.roomId = roomId;
        this.requiresHospitalization = requiresHospitalization;
        this.admissionReason = admissionReason;
        this.currentIllnessHistory = currentIllnessHistory;
        this.physicalExamination = physicalExamination;
        this.updatedBy = updatedBy;
    }

    public static UpdateSurgeryCommand fromRequest(UpdateSurgeryRequest request) {
        return new UpdateSurgeryCommand(
                request.getSurgeryId(),
                request.getPatientId(),
                request.getDoctorId(),
                request.getSpecialtyId(),
                request.getSurgeryType(),
                request.getDescription(),
                request.getScheduledDate(),
                request.getEstimatedDurationMinutes(),
                request.getComplexityLevel(),
                request.getRoomId(),
                request.getRequiresHospitalization(),
                request.getAdmissionReason(),
                request.getCurrentIllnessHistory(),
                request.getPhysicalExamination(),
                request.getUpdatedBy()
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateSurgeryMessage(id);
    }
}