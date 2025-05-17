package com.kynsoft.medicaltest.application.command.examinationorder.addexamination;

import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class AddExaminationToOrderCommand implements ICommand {
    private UUID orderId;
    private UUID examinationId;
    private UUID userId;
    
    private AddExaminationToOrderCommand(UUID orderId, UUID examinationId, UUID userId) {
        this.orderId = orderId;
        this.examinationId = examinationId;
        this.userId = userId;
    }
    
    public static AddExaminationToOrderCommand create(UUID orderId, UUID examinationId, String userId) {
        return new AddExaminationToOrderCommand(orderId, examinationId, UUID.fromString(userId));
    }
    
    @Override
    public ICommandMessage getMessage() {
        return new AddExaminationToOrderMessage(this.orderId);
    }
}
