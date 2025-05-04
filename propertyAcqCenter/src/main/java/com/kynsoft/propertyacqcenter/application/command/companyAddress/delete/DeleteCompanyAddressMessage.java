package com.kynsoft.propertyacqcenter.application.command.companyAddress.delete;

import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class DeleteCompanyAddressMessage implements ICommandMessage {

    private final String command = "DELETE_COMPANY_ADDRESS";

    private final UUID id;
}
