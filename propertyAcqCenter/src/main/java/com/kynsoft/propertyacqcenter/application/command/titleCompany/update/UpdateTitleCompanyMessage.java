package com.kynsoft.propertyacqcenter.application.command.titleCompany.update;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class UpdateTitleCompanyMessage implements ICommandMessage {
    private final UUID id;
    private final String command = "UPDATE_TITLE_COMPANY";
}