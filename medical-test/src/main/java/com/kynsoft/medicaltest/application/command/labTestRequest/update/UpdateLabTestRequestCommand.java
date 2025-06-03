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
    private String status;
    private String observations;
    private UUID updateBy;

    public static UpdateLabTestRequestCommand fromRequest(UpdateLabTestRequestRequest request, UUID id, UUID userId) {
        return UpdateLabTestRequestCommand.builder()
                .id(id)
                .status(request.getStatus())
                .observations(request.getObservations())
                .updateBy(userId)
                .build();
    }

    @Override
    public ICommandMessage getMessage() {
        return new UpdateLabTestRequestMessage(id);
    }
}
