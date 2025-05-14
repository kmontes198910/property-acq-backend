package com.kynsoft.propertyacqcenter.application.command.ownerShipLegalEntity.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DeleteOwnerShipLegalEntityMessage implements ICommandMessage {
    private final String command = "DELETE_OWNER_SHIP";

    private final UUID id;

}
