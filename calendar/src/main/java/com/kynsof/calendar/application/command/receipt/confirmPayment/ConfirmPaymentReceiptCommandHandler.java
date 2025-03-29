package com.kynsof.calendar.application.command.receipt.confirmPayment;

import com.kynsof.calendar.application.events.CreatePaymentGroupEvent;
import com.kynsof.calendar.domain.dto.ReceiptDto;
import com.kynsof.calendar.domain.dto.enumType.EStatusReceipt;
import com.kynsof.calendar.domain.dto.enumType.EStatusSchedule;
import com.kynsof.calendar.domain.service.IReceiptService;
import com.kynsof.calendar.application.service.http.CreateBillingPartialRequest;
import com.kynsof.calendar.application.service.http.CreateGroupPaymentUnifRequest;
import com.kynsof.calendar.application.service.http.GroupPaymentServiceClient;
import com.kynsof.share.core.application.payment.domain.placeToPlay.PaymentServiceStatusResponse;
import com.kynsof.share.core.application.payment.infrastructure.service.config.PaymentServiceClient;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import com.kynsof.share.core.domain.exception.BusinessException;
import com.kynsof.share.core.domain.exception.DomainErrorMessage;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class ConfirmPaymentReceiptCommandHandler implements ICommandHandler<ConfirmPaymentReceiptCommand> {

    private final IReceiptService receiptService;
    private final PaymentServiceClient paymentServiceClient;
    private final GroupPaymentServiceClient groupPaymentServiceClient;
    private final ApplicationEventPublisher eventPublisher;

    public ConfirmPaymentReceiptCommandHandler(IReceiptService receiptService,
                                               PaymentServiceClient paymentServiceClient,
                                               GroupPaymentServiceClient groupPaymentServiceClient,
                                               ApplicationEventPublisher eventPublisher) {
        this.receiptService = receiptService;
        this.paymentServiceClient = paymentServiceClient;
        this.groupPaymentServiceClient = groupPaymentServiceClient;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void handle(ConfirmPaymentReceiptCommand command) {
        try {
            ReceiptDto receipt = receiptService.findById(command.getReceiptId());
            if (command.getStatus() == EStatusReceipt.PAYMENT) {
                PaymentServiceStatusResponse paymentStatus = paymentServiceClient.validateStatusPayment(
                        command.getRequestId(), receipt.getSchedule().getBusiness().getId());

                if ("APPROVED".equals(paymentStatus.getStatus())) {
                    processApprovedPayment(receipt, paymentStatus, command.getAuthorizationCode());
                }
            } else if (command.getStatus() == EStatusReceipt.CANCEL || command.getStatus() == EStatusReceipt.REJECTED) {
                processFailedPayment(receipt, command.getStatus());
            }

        } catch (IOException e) {
            throw new BusinessException(DomainErrorMessage.PAYMENT_NOT_FOUND, "Error al consultar el servicio de pagos: " + e.getMessage());
        } catch (Exception e) {
            throw new BusinessException(DomainErrorMessage.PAYMENT_NOT_FOUND, "Error durante el procesamiento del pago: " + e.getMessage());
        }
    }

    private void processApprovedPayment(ReceiptDto receipt, PaymentServiceStatusResponse paymentStatus, String authorizationCode) {
        receipt.setReference(paymentStatus.getReference());
        receipt.setAuthorizationCode(authorizationCode);
        receipt.setStatus(EStatusReceipt.PAYMENT);
        receipt.getSchedule().setStatus(EStatusSchedule.SOLD_OUT);
        receiptService.update(receipt);

        CreateGroupPaymentUnifRequest request = buildGroupPaymentRequest(receipt, paymentStatus);
        eventPublisher.publishEvent(new CreatePaymentGroupEvent(request, receipt.getId()));
    }

    private void processFailedPayment(ReceiptDto receipt, EStatusReceipt newStatus) {
        receipt.getSchedule().setStock(receipt.getSchedule().getStock() + 1);
        receipt.getSchedule().setStatus(EStatusSchedule.AVAILABLE);
        receipt.setStatus(newStatus);
        receiptService.update(receipt);
    }

    private CreateGroupPaymentUnifRequest buildGroupPaymentRequest(ReceiptDto receipt, PaymentServiceStatusResponse paymentStatus) {
        CreateBillingPartialRequest billing = new CreateBillingPartialRequest();
        billing.setCode(receipt.getService().getCode());
        billing.setDescription(receipt.getService().getName());
        billing.setCost(receipt.getPrice());

        CreateGroupPaymentUnifRequest request = new CreateGroupPaymentUnifRequest();
        request.setClientId(receipt.getUser().getId());
        request.setBusinessId(receipt.getSchedule().getBusiness().getId());
        request.setPaymentType("PLACETOPAY");
        request.setPaymentStatus("PAYMENT_APPROVED");
        request.setAuthorizationCode(paymentStatus.getAuthorization());
        request.setReference(paymentStatus.getReference());
        request.setTypeOperation("ExternalConsult");
        request.setProforma(false);
        request.setBillings(List.of(billing));
        return request;
    }
}
