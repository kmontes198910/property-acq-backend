package com.kynsoft.cirugia.application.command.medicalteam.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class CreateMedicalTeamCommand implements ICommand {

    private UUID id;
    private UUID surgeryId;
    private UUID memberId;
    private String memberName;
    private String memberLastName;
    private String specialtyName;
    private String specialtyCode;
    private String role;
    private UUID businessId;
    private UUID createdBy;
    private LocalDateTime createdAt;

    public CreateMedicalTeamCommand(UUID surgeryId, UUID memberId, String memberName, String memberLastName,
                                    String specialtyName, String specialtyCode, String role,
                                    UUID businessId, UUID createdBy) {
        this.id = UUID.randomUUID();
        this.surgeryId = surgeryId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberLastName = memberLastName;
        this.specialtyName = specialtyName;
        this.specialtyCode = specialtyCode;
        this.role = role;
        this.businessId = businessId;
        this.createdBy = createdBy;
        this.createdAt = LocalDateTime.now();
    }

    public static CreateMedicalTeamCommand fromRequest(CreateMedicalTeamRequest request, UUID createdBy) {
        return new CreateMedicalTeamCommand(
                request.getSurgeryId(),
                request.getMemberId(),
                request.getMemberName(),
                request.getMemberLastName(),
                request.getSpecialtyName(),
                request.getSpecialtyCode(),
                request.getRole(),
                request.getBusinessId(),
                createdBy
        );
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateMedicalTeamMessage(id);
    }
}