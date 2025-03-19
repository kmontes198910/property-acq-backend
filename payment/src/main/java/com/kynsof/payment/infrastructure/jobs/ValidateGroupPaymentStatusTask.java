package com.kynsof.payment.infrastructure.jobs;

import com.kynsof.payment.domain.dto.enumDto.GroupPaymentStatus;
import com.kynsof.payment.domain.service.IGroupPaymentService;
import com.kynsof.payment.infrastructure.entity.GroupPayment;
import com.kynsof.share.core.application.payment.domain.placeToPlay.PaymentServiceStatusResponse;
import com.kynsof.share.core.application.payment.infrastructure.service.config.ExternalServiceClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;


@Component
public class ValidateGroupPaymentStatusTask {

    private final IGroupPaymentService groupPaymentService;
    private final ExternalServiceClient paymentServiceClient;
    private static final Logger logger = LoggerFactory.getLogger(ValidateGroupPaymentStatusTask.class);

    public ValidateGroupPaymentStatusTask(IGroupPaymentService receiptService, ExternalServiceClient paymentServiceClient) {
        this.groupPaymentService = receiptService;
        this.paymentServiceClient = paymentServiceClient;
    }

    @Scheduled(cron = "0 */5 * * * *")
    public void updateStatusPayment() {
        List<GroupPayment> groupPayments = this.groupPaymentService.findByStatus(GroupPaymentStatus.PENDING_APPROVED);
        groupPayments.forEach(groupPayment -> {
            try {
                PaymentServiceStatusResponse paymentStatus = paymentServiceClient.validateStatusPayment(
                        groupPayment.getRequestId(), groupPayment.getBusiness().getId());
                GroupPaymentStatus groupPaymentStatus = GroupPaymentStatus.PENDING_APPROVED;
                if (paymentStatus.getStatus().equals("APPROVED")) {
                    groupPaymentStatus = GroupPaymentStatus.PAYMENT_APPROVED;
                } else if (paymentStatus.getStatus().equals("REJECTED")) {
                    groupPaymentStatus = GroupPaymentStatus.REJECTED;
                }
                this.groupPaymentService.update(groupPayment.getId(), paymentStatus.getReference(), paymentStatus.getAuthorization(), groupPayment.getRequestId(),
                        "", groupPaymentStatus
                );
                System.err.println(paymentStatus);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        });

    }

}
