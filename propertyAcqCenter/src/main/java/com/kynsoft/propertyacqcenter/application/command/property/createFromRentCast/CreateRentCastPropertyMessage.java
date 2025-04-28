package com.kynsoft.propertyacqcenter.application.command.property.createFromRentCast;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class CreateRentCastPropertyMessage implements ICommandMessage {

    private final String command = "CREATE_RENT_CAST_PROPERTIES";
    private final List<UUID> id;
}
