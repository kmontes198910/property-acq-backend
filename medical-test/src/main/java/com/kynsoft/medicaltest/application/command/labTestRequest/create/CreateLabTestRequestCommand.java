package com.kynsoft.medicaltest.application.command.labTestRequest.create;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import java.time.LocalDate;
import java.util.List;
import lombok.*;

import java.util.UUID;

@Getter
@Builder
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateLabTestRequestCommand implements ICommand {
    private UUID id;
    private UUID patientId;
    private UUID doctorId;
    private LocalDate creationDate;
    private String status;
    private String observations;
    private UUID businessId;
    private UUID createdBy;
    private List<LabTestItemRequestRequest> labTestItems;
    private boolean isActive;

    public static CreateLabTestRequestCommand fromRequest(CreateLabTestRequestRequest request, UUID userId) {
        return CreateLabTestRequestCommand.builder()
                .id(UUID.randomUUID())
                .patientId(request.getPatientId())
                .doctorId(request.getDoctorId())
                .creationDate(LocalDate.now())
                .status(request.getStatus())
                .observations(request.getObservations())
                .businessId(request.getBusinessId())
                .createdBy(userId)
                .labTestItems(request.getLabTestItems())
                .isActive(request.isActive())
                .build();
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateLabTestRequestMessage(id);
    }
}
