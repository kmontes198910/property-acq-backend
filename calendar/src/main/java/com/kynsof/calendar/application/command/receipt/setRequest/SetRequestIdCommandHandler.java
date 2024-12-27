package com.kynsof.calendar.application.command.receipt.setRequest;

import com.kynsof.calendar.domain.dto.ReceiptDto;
import com.kynsof.calendar.domain.dto.enumType.EStatusReceipt;
import com.kynsof.calendar.domain.service.IReceiptService;
import com.kynsof.calendar.infrastructure.service.kafka.producer.ProducerGenerateReportEventService;
import com.kynsof.share.core.application.payment.domain.placeToPlay.PaymentServiceStatusResponse;
import com.kynsof.share.core.application.payment.infrastructure.service.config.ExternalServiceClient;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.exception.BusinessException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SetRequestIdCommandHandler implements ICommandHandler<SetRequestIdCommand> {

    private final IReceiptService service;
    private final ExternalServiceClient paymentServiceClient;

    public SetRequestIdCommandHandler(IReceiptService service, ExternalServiceClient paymentServiceClient,
                                      ProducerGenerateReportEventService producerGenerateReportEventService) {
        this.service = service;
        this.paymentServiceClient = paymentServiceClient;
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