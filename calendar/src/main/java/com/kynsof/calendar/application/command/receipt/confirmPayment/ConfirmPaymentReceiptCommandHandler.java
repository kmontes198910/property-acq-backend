package com.kynsof.calendar.application.command.receipt.confirmPayment;

import com.kynsof.calendar.domain.dto.ReceiptDto;
import com.kynsof.calendar.domain.dto.enumType.EStatusReceipt;
import com.kynsof.calendar.domain.service.IReceiptService;
import com.kynsof.calendar.infrastructure.service.kafka.producer.ProducerGenerateReportEventService;
import com.kynsof.share.core.application.payment.domain.placeToPlay.response.TransactionsState;
import com.kynsof.share.core.application.payment.domain.service.IPaymentServiceClient;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.exception.BusinessException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import org.springframework.stereotype.Component;

@Component
public class ConfirmPaymentReceiptCommandHandler implements ICommandHandler<ConfirmPaymentReceiptCommand> {

    private final IReceiptService service;
    private final IPaymentServiceClient paymentServiceClient;

    public ConfirmPaymentReceiptCommandHandler(IReceiptService service, IPaymentServiceClient paymentServiceClient,
                                               ProducerGenerateReportEventService producerGenerateReportEventService) {
        this.service = service;
        this.paymentServiceClient = paymentServiceClient;
    }

    @Override
    public void handle(ConfirmPaymentReceiptCommand command) {
//        TransactionsState transactionsState = paymentServiceClient.getTransactionsState(Integer.parseInt(command.getRequestId()));
//        if(transactionsState == null || !transactionsState.getValue().getStatus().getStatus().equals("APPROVED")) {
//            throw new BusinessException(DomainErrorMessage.PAYMENT_NOT_FOUND, DomainErrorMessage.PAYMENT_NOT_FOUND.toString());
//        }
        ReceiptDto _receipt = this.service.findById(command.getReceiptId());
        _receipt.setAuthorizationCode(command.getAuthorizationCode());
        _receipt.setRequestId(command.getRequestId());
        _receipt.setReference(command.getReference());
        _receipt.setSessionId(command.getSessionId());
        _receipt.setIpAddressPayment(command.getIpAddress());
        _receipt.setUserAgentPayment(command.getUserAgent());
        _receipt.setStatus(command.getStatus());


        if (command.getStatus().equals(EStatusReceipt.CANCEL)) {
            //TO DO
            //Liverar el stock
            //Enviar Correo de cancelado
            _receipt.setStatus(command.getStatus());
            _receipt.getSchedule().setStock(_receipt.getSchedule().getStock() + 1);

            //  cleanStock(_schedule);

        }
        if (command.getStatus().equals(EStatusReceipt.REJECTED)) {
            _receipt.getSchedule().setStock(_receipt.getSchedule().getStock() + 1);
            //TO DO
            //Validar el estado del pago, si el estado es pendiente de pago o pago hacer el proceso de confirmado ,sino cambiar el estado
            //  cleanStock(_schedule);

            //Enviar Correo de cancelado
            _receipt.setStatus(command.getStatus());

        }
//        if (command.getStatus().equals(EStatusReceipt.PENDING)) {
//            //TODO
//
//            _receipt.setStatus(command.getStatus());
//        }

        service.update(_receipt);
    }

}
