package com.kynsof.calendar.application.events;

import com.kynsof.calendar.application.service.http.GroupPaymentServiceClient;
import com.kynsof.calendar.domain.service.IReceiptService;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class CreatePaymentGroupEventHandler {
    private final GroupPaymentServiceClient groupPaymentServiceClient;
    private final IReceiptService receiptService;
    public CreatePaymentGroupEventHandler(GroupPaymentServiceClient groupPaymentServiceClient, IReceiptService receiptService) {
        this.groupPaymentServiceClient = groupPaymentServiceClient;
        this.receiptService = receiptService;
    }

    @EventListener
    public void onApplicationEvent(CreatePaymentGroupEvent event) {
        try {
            String groupPaymentId = groupPaymentServiceClient.createCompleted(event.getCreateGroupPayment());
            receiptService.updateGroupPaymentId(event.getReceiptId(),groupPaymentId);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
