package com.kynsof.calendar.application.command.receipt.setRequest;

import com.kynsof.calendar.domain.dto.ReceiptDto;
import com.kynsof.calendar.domain.service.IReceiptService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

@Component
public class SetRequestIdCommandHandler implements ICommandHandler<SetRequestIdCommand> {

    private final IReceiptService service;

    public SetRequestIdCommandHandler(IReceiptService service) {
        this.service = service;
    }

    @Override
    public void handle(SetRequestIdCommand command) {
       ReceiptDto receiptDto = service.findById(command.getReceiptId());
       receiptDto.setRequestId(command.getRequestId());
       receiptDto.setStatus(command.getStatus());
       receiptDto.setProcessUrl(command.getProcessUrl());
       receiptDto.setReference(command.getReference());
       service.update(receiptDto);
    }
}