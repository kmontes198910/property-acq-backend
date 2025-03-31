package com.kynsof.calendar.application.command.receipt.changeResource;

import com.kynsof.calendar.domain.dto.enumType.EStatusReceipt;
import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class ChangeResourceReceiptCommand implements ICommand {
    private  UUID id ;
    private UUID resourceId;


    public static ChangeResourceReceiptCommand fromRequest(UUID id, ChangeResourceReceiptRequest request) {
        return new ChangeResourceReceiptCommand(id, request.getResourceId());
    }


    @Override
    public ICommandMessage getMessage() {
        return new ChangeResourceReceiptMessage(id);
    }
}
