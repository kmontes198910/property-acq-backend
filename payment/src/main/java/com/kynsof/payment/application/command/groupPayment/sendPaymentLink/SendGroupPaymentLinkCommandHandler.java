package com.kynsof.payment.application.command.groupPayment.sendPaymentLink;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kynsof.payment.domain.dto.GroupPaymentDto;
import com.kynsof.payment.domain.service.IGroupPaymentService;
import com.kynsof.payment.infrastructure.service.http.EmailService;
import com.kynsof.share.core.domain.bus.command.ICommandHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

@Component
public class SendGroupPaymentLinkCommandHandler implements ICommandHandler<SendGroupPaymentLinkCommand> {

    private final IGroupPaymentService serviceImpl;
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;


    private  final  EmailService emailService;

    public SendGroupPaymentLinkCommandHandler(IGroupPaymentService serviceImpl, ObjectMapper objectMapper, EmailService emailService) {
        this.serviceImpl = serviceImpl;
        this.emailService = emailService;
        this.httpClient = HttpClient.newHttpClient(); // Instanciación directa
        this.objectMapper = objectMapper;
    }

    @Override
    public void handle(SendGroupPaymentLinkCommand command) {
        try {
            GroupPaymentDto groupPaymentDto = this.serviceImpl.findById(command.getId());
            String paymentLink = "https://payments.doctorkyn.com/payment-info?token="+command.getId();
            boolean emailSent = emailService.sendEmail(
                    List.of(Map.of("email", groupPaymentDto.getClient().getEmail(), "name", groupPaymentDto.getClient().getName()+ " "+ groupPaymentDto.getClient().getLastName())),
                    List.of(
                            Map.of("Key", "payment_link", "Value", paymentLink),
                            Map.of("Key", "reference_number", "Value", groupPaymentDto.getReference()),
                            Map.of("Key", "invoice_amount", "Value", String.valueOf(groupPaymentDto.getTotalAmount())),
                            Map.of("Key", "client_name", "Value", groupPaymentDto.getClient().getName()+ " "+ groupPaymentDto.getClient().getLastName())
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


        } catch (Exception e) {
            System.err.println("❌ Exception sending email: " + e.getMessage());
            e.printStackTrace();
        }
    }
}