package com.kynsoft.medicaltest.application.command.examinationorder.removeexamination;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class RemoveExaminationFromOrderCommand implements ICommand {
    private UUID orderId;
    private UUID examinationId;
    private UUID userId;
    
    private RemoveExaminationFromOrderCommand(UUID orderId, UUID examinationId, UUID userId) {
        this.orderId = orderId;
        this.examinationId = examinationId;
        this.userId = userId;
    }
    
    public static RemoveExaminationFromOrderCommand create(UUID orderId, UUID examinationId, String userId) {
        return new RemoveExaminationFromOrderCommand(orderId, examinationId, UUID.fromString(userId));
    }
    
    @Override
    public ICommandMessage getMessage() {
        return new RemoveExaminationFromOrderMessage(this.orderId);
    }
}
