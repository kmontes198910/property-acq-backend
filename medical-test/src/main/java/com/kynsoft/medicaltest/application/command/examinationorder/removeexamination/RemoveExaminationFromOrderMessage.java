package com.kynsoft.medicaltest.application.command.examinationorder.removeexamination;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RemoveExaminationFromOrderMessage implements ICommandMessage {
    private UUID orderId;
}
