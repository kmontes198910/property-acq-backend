package com.kynsof.calendar.application.command.receipt.confirmPayment;

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
public class ConfirmPaymentReceiptCommandHandler implements ICommandHandler<ConfirmPaymentReceiptCommand> {

    private final IReceiptService service;
    private final ExternalServiceClient paymentServiceClient;

    public ConfirmPaymentReceiptCommandHandler(IReceiptService service, ExternalServiceClient paymentServiceClient,
                                               ProducerGenerateReportEventService producerGenerateReportEventService) {
        this.service = service;
        this.paymentServiceClient = paymentServiceClient;
    }

    @Override
    public void handle(ConfirmPaymentReceiptCommand command) {
        PaymentServiceStatusResponse paymentStatus;

        try {
            // Llamar al servicio externo para obtener el estado del pago
            paymentStatus = paymentServiceClient.callExternalService(command.getRequestId(), "279e33bd-471c-42ec-b0ac-ada8d82c6270");

            if (paymentStatus == null || !paymentStatus.getStatus().equals("APPROVED")) {
                throw new BusinessException(DomainErrorMessage.PAYMENT_NOT_FOUND, "Payment not approved or not found.");
            }

            // Actualizar el recibo con la información de pago
            ReceiptDto _receipt = this.service.findById(command.getReceiptId());
            _receipt.setAuthorizationCode(paymentStatus.getAuthorization());
            _receipt.setRequestId(command.getRequestId());
            _receipt.setReference(paymentStatus.getReference());

            if (command.getStatus().equals(EStatusReceipt.CANCEL)) {
                _receipt.getSchedule().setStock(_receipt.getSchedule().getStock() + 1);
                _receipt.setStatus(command.getStatus());
            }

            if (command.getStatus().equals(EStatusReceipt.REJECTED)) {
                _receipt.getSchedule().setStock(_receipt.getSchedule().getStock() + 1);
                _receipt.setStatus(command.getStatus());
            }

            // Guardar los cambios en el recibo
            service.update(_receipt);

        } catch (IOException e) {
            // Manejo de errores durante la llamada al servicio
            System.err.println("Error al consultar el servicio de pagos: " + e.getMessage());
            throw new BusinessException(DomainErrorMessage.PAYMENT_NOT_FOUND, "Error al consultar el servicio de pagos.");
        } catch (Exception e) {
            // Manejo de otros errores durante el proceso
            System.err.println("Error durante el procesamiento del pago: " + e.getMessage());
            throw new BusinessException(DomainErrorMessage.PAYMENT_NOT_FOUND, "Error durante el procesamiento del pago.");
        }
    }
}