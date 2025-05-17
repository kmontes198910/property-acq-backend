package com.kynsoft.medicaltest.application.command.examination.complete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CompleteExaminationMessage implements ICommandMessage {
    private UUID id;
}
