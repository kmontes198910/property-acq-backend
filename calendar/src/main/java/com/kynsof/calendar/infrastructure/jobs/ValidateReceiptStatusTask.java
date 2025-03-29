package com.kynsof.calendar.infrastructure.jobs;

import com.kynsof.calendar.application.events.CreatePaymentGroupEvent;
import com.kynsof.calendar.application.service.http.CreateBillingPartialRequest;
import com.kynsof.calendar.application.service.http.CreateGroupPaymentUnifRequest;
import com.kynsof.calendar.domain.dto.ReceiptDto;
import com.kynsof.calendar.domain.dto.ReceiptSummaryDTO;
import com.kynsof.calendar.domain.dto.enumType.EStatusReceipt;
import com.kynsof.calendar.domain.dto.enumType.EStatusSchedule;
import com.kynsof.calendar.domain.service.IReceiptService;
import com.kynsof.share.core.application.payment.domain.placeToPlay.PaymentServiceStatusResponse;
import com.kynsof.share.core.application.payment.infrastructure.service.config.PaymentServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;


@Component
public class ValidateReceiptStatusTask {

    private final IReceiptService receiptService;
    private final PaymentServiceClient paymentServiceClient;
    private static final Logger logger = LoggerFactory.getLogger(ValidateReceiptStatusTask.class);
    private final ApplicationEventPublisher eventPublisher;

    public ValidateReceiptStatusTask(IReceiptService receiptService, PaymentServiceClient paymentServiceClient, ApplicationEventPublisher eventPublisher) {
        this.receiptService = receiptService;
        this.paymentServiceClient = paymentServiceClient;
        this.eventPublisher = eventPublisher;
    }

    @Scheduled(cron = "* */5 * * * *")
    public void updateStatusPayment() {
        List<ReceiptSummaryDTO> receipts = this.receiptService.findByStatus(EStatusReceipt.PENDING_PAY);
        receipts.forEach(receipt -> {
            try {
                PaymentServiceStatusResponse paymentStatus = paymentServiceClient.validateStatusPayment(receipt.getRequestId(), receipt.getBusinessId());
                if (paymentStatus != null && !paymentStatus.getStatus().equals("PENDING")) {
                    System.err.println("Estado del pago:"+paymentStatus);
                    this.receiptService.updatePaymentStatus(receipt.getId(), paymentStatus.getStatus(), paymentStatus.getReference(), paymentStatus.getAuthorization());
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        });

    }
    private void processApprovedPayment(ReceiptDto receipt, PaymentServiceStatusResponse paymentStatus, String authorizationCode) {
        receipt.setReference(paymentStatus.getReference());
        receipt.setAuthorizationCode(authorizationCode);
        receipt.setStatus(EStatusReceipt.PAYMENT);
        receipt.getSchedule().setStock(receipt.getSchedule().getStock() - 1);
        if (receipt.getSchedule().getStock() == 0) {
            receipt.getSchedule().setStatus(EStatusSchedule.SOLD_OUT);
        }
        receiptService.update(receipt);

        CreateGroupPaymentUnifRequest request = buildGroupPaymentRequest(receipt, paymentStatus);
        eventPublisher.publishEvent(new CreatePaymentGroupEvent(request, receipt.getId()));
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
