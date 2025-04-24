package com.kynsoft.propertyacqcenter.application.command.legalEntity.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DeleteLegalEntityMessage implements ICommandMessage {
    private final String command = "DELETE_LEGAL_ENTITY";

    private final UUID id;

}
