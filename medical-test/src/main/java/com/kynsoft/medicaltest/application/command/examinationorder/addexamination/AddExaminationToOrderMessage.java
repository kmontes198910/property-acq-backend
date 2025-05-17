package com.kynsoft.medicaltest.application.command.examinationorder.addexamination;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddExaminationToOrderMessage implements ICommandMessage {
    private UUID orderId;
}
