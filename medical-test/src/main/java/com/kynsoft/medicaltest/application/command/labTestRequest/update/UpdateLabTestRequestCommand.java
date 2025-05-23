package com.kynsoft.medicaltest.application.command.labTestRequest.update;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import java.time.LocalDate;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateLabTestRequestCommand implements ICommand {
    private UUID id;
    private UUID patientId;
    private UUID doctorId;
    private LocalDate creationDate;
    private String status;
    private String observations;
    private UUID businessId;
    private UUID updateBy;
    private boolean isActive;

    public static UpdateLabTestRequestCommand fromRequest(UpdateLabTestRequestRequest request, UUID id, UUID userId) {
        return UpdateLabTestRequestCommand.builder()
                .id(id)
                .patientId(request.getPatientId())
                .doctorId(request.getDoctorId())
                .creationDate(request.getCreationDate())
                .status(request.getStatus())
                .observations(request.getObservations())
                .businessId(request.getBusinessId())
                .updateBy(userId)
                .isActive(request.isActive())
                .build();
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateLabTestRequestMessage(id);
    }
}
