package com.kynsoft.propertyacqcenter.application.command.titleCompany.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DeleteTitleCompanyMessage implements ICommandMessage {

    private final String command = "DELETE_TITLE_COMPANY";

    private final UUID id;
}
