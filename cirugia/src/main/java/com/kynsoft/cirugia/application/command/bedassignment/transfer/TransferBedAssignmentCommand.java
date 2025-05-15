package com.kynsoft.cirugia.application.command.bedassignment.transfer;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransferBedAssignmentCommand implements ICommand {
    private UUID id;

    private UUID bedId;
    private UUID roomId;
    private UUID createdBy;
    
    private TransferBedAssignmentMessage message;
    
    public static TransferBedAssignmentCommand fromRequest(TransferBedAssignmentRequest request, String userId) {
        return TransferBedAssignmentCommand.builder()
                .bedId(request.getBedId())
                .roomId(request.getRoomId())
                .createdBy(UUID.fromString(userId))
                .build();
    }
    
    @Override
    public ICommandMessage getMessage() {
        return message != null ? message : new TransferBedAssignmentMessage();
    }
}
