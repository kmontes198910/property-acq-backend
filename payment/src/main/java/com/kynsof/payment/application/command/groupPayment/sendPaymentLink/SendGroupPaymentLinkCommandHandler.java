package com.kynsof.payment.application.command.groupPayment.sendPaymentLink;

import com.kynsof.payment.domain.dto.GroupPaymentDto;
import com.kynsof.payment.domain.service.IGroupPaymentService;
import com.kynsof.payment.infrastructure.service.http.SendNotification;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class SendGroupPaymentLinkCommandHandler implements ICommandHandler<SendGroupPaymentLinkCommand> {

    private final IGroupPaymentService serviceImpl;
    private final SendNotification notificationService;

    public SendGroupPaymentLinkCommandHandler(IGroupPaymentService serviceImpl, SendNotification notificationService) {
        this.serviceImpl = serviceImpl;
        this.notificationService = notificationService;
    }

    @Override
    public void handle(SendGroupPaymentLinkCommand command) {
        try {
            GroupPaymentDto groupPaymentDto = this.serviceImpl.findById(command.getId());
            String paymentLink = "https://payments.doctorkyn.com/payment-info?token=" + command.getId();

            if ("EMAIL".equals(command.getType())) {
                sendEmailNotification(groupPaymentDto, paymentLink);
            } else if ("WHATSAPP".equals(command.getType())) {
                sendWhatsAppNotification(groupPaymentDto);
            }
        } catch (Exception e) {
            System.err.println("❌ Exception sending notification: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void sendEmailNotification(GroupPaymentDto groupPaymentDto, String paymentLink) {
        boolean emailSent = notificationService.sendEmail(
                List.of(Map.of("email", groupPaymentDto.getClient().getEmail(), "name", groupPaymentDto.getClient().getName() + " " + groupPaymentDto.getClient().getLastName())),
                List.of(
                        Map.of("Key", "payment_link", "Value", paymentLink),
                        Map.of("Key", "reference_number", "Value", groupPaymentDto.getInternalReferenceNumber()),
                        Map.of("Key", "invoice_amount", "Value", String.valueOf(groupPaymentDto.getTotalAmount())),
                        Map.of("Key", "client_name", "Value", groupPaymentDto.getClient().getName() + " " + groupPaymentDto.getClient().getLastName())
                ),
                List.of(), // Adjuntos vacíos
                "Send link",
                "6784483"
        );

        if (emailSent) {
            System.out.println("✅ Correo enviado correctamente.");
        } else {
            System.err.println("❌ Error al enviar el correo.");
        }
    }

    private void sendWhatsAppNotification(GroupPaymentDto groupPaymentDto) {
        String paymentLink = "payment-info?token=" + groupPaymentDto.getId();
        Map<String, String> requestData = buildWhatsAppRequestData(groupPaymentDto, paymentLink);
        String response = notificationService.sendWhatsAppNotification(requestData);
        System.out.println("📲 WhatsApp Response: " + response);
    }

    private Map<String, String> buildWhatsAppRequestData(GroupPaymentDto groupPaymentDto, String paymentLink) {
        Map<String, String> requestData = new HashMap<>();
        requestData.put("phone", groupPaymentDto.getClient().getPhone());
        requestData.put("type", "template");
        requestData.put("template_name", "doctor_kyn_paymently");
        requestData.put("customer_name", groupPaymentDto.getClient().getName());
        requestData.put("invoice_amount", String.valueOf(groupPaymentDto.getTotalAmount()));
        requestData.put("reference_number", groupPaymentDto.getInternalReferenceNumber());
        requestData.put("payment_url", paymentLink);
        return requestData;
    }
}
