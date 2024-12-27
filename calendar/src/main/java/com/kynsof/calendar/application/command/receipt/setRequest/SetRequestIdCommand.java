package com.kynsof.calendar.application.command.receipt.setRequest;

import com.kynsof.calendar.domain.dto.enumType.EStatusReceipt;
import com.kynsof.share.core.domain.bus.command.ICommand;
import com.kynsof.share.core.domain.bus.command.ICommandMessage;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class SetRequestIdCommand implements ICommand {

    private final UUID receiptId;
    private final EStatusReceipt status;
    private final String requestId;
    private final String processUrl;
    private final String reference;

    public SetRequestIdCommand(UUID receiptId, EStatusReceipt status, String requestId, String processUrl, String reference) {
        this.receiptId = receiptId;
        this.status = status;
        this.requestId = requestId;
        this.processUrl = processUrl;
        this.reference = reference;
    }


    public static SetRequestIdCommand fromRequest(UUID receiptId, SetRequestIdRequest request) {
        return new SetRequestIdCommand(receiptId, request.getStatus(), request.getRequestId(), request.getProcessUrl(),
                request.getReference());
    }

    @Override
    public ICommandMessage getMessage() {
        return new SetRequestIdMessage(receiptId);
    }
}
