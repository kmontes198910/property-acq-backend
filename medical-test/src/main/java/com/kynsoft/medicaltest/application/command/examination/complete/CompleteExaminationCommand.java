package com.kynsoft.medicaltest.application.command.examination.complete;


import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CompleteExaminationCommand implements ICommand {
    private UUID id;
    private String results;
    private UUID userId;
    
    private CompleteExaminationCommand(UUID id, String results, UUID userId) {
        this.id = id;
        this.results = results;
        this.userId = userId;
    }
    
    public static CompleteExaminationCommand fromRequest(UUID id, String results, String userId) {
        return new CompleteExaminationCommand(id, results, UUID.fromString(userId));
    }
    
    @Override
    public ICommandMessage getMessage() {
        return new CompleteExaminationMessage(this.id);
    }
}
