package com.kynsoft.medicaltest.application.command.labTestItemRequest.create;

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
public class CreateLabTestItemRequestCommand implements ICommand {
    private UUID id;
    private UUID order;
    private String code;
    private String examinationType;
    private String status;
    private LocalDate completionDate;
    private String observations;
    private UUID createdBy;

    public static CreateLabTestItemRequestCommand fromRequest(CreateLabTestItemRequestRequest request, UUID userId) {
        return CreateLabTestItemRequestCommand.builder()
                .id(UUID.randomUUID())
                .status(request.getStatus())
                .observations(request.getObservations())
                .createdBy(userId)
                .code(request.getCode())
                .completionDate(request.getCompletionDate())
                .examinationType(request.getExaminationType())
                .order(request.getOrder())
                .build();
    }

    @Override
    public ICommandMessage getMessage() {
        return new CreateLabTestItemRequestMessage(id);
    }
}
