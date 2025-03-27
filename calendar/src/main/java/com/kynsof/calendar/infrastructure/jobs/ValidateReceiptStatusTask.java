package com.kynsof.calendar.infrastructure.jobs;

import com.kynsof.calendar.domain.dto.enumType.EStatusReceipt;
import com.kynsof.calendar.domain.service.IReceiptService;
import com.kynsof.calendar.infrastructure.entity.Receipt;
import com.kynsof.share.core.application.payment.domain.placeToPlay.PaymentServiceStatusResponse;
import com.kynsof.share.core.application.payment.infrastructure.service.config.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;


@Component
public class ValidateReceiptStatusTask {

    private final IReceiptService receiptService;
    private final ExternalServiceClient paymentServiceClient;
    private static final Logger logger = LoggerFactory.getLogger(ValidateReceiptStatusTask.class);

    public ValidateReceiptStatusTask(IReceiptService receiptService, ExternalServiceClient paymentServiceClient) {
        this.receiptService = receiptService;
        this.paymentServiceClient = paymentServiceClient;
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void updateStatusPayment() {
        List<Receipt> receipts = this.receiptService.findByStatus(EStatusReceipt.PENDING_PAY);
        receipts.forEach(receipt -> {
            try {
                PaymentServiceStatusResponse paymentStatus = paymentServiceClient.validateStatusPayment(receipt.getRequestId(), receipt.getSchedule().getBusiness().getId());
                System.err.println("Estado del pago:"+paymentStatus);
                if (paymentStatus.getStatus().equals("APPROVED")) {
                    this.receiptService.updatePaymentStatus(receipt, paymentStatus.getStatus(), paymentStatus.getReference(), paymentStatus.getAuthorization());
                }
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        });

    }

}
