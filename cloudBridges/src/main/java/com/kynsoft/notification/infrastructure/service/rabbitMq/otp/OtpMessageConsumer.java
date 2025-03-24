package com.kynsoft.notification.infrastructure.service.rabbitMq.otp;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kynsof.share.core.infrastructure.bus.IMediator;
import com.kynsoft.notification.application.command.sendMailjetEmail.SendMailJetEMailCommand;
import com.kynsoft.notification.domain.dto.MailJetRecipient;
import com.kynsoft.notification.domain.dto.MailJetVar;
import com.kynsoft.notification.domain.dto.MailjetTemplateEnum;
import com.kynsoft.notification.infrastructure.service.rabbitMq.RabbitMQConfig;
import jakarta.validation.constraints.NotNull;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class OtpMessageConsumer {

    private final ObjectMapper objectMapper;
    private final IMediator mediator;

    public OtpMessageConsumer(ObjectMapper objectMapper, IMediator mediator) {
        this.objectMapper = objectMapper;
        this.mediator = mediator;
    }

    @RabbitListener(queues = RabbitMQConfig.OTP_QUEUE)
    public void receiveOtpMessage(String message) {
        try {
            OtpMessageDTO otpMessage = objectMapper.readValue(message, OtpMessageDTO.class);

            // Generar el mensaje de confirmación
            String otpText = String.format(
                    "🔑 Código OTP para %s: %s\nEnviado al correo: %s",
                    otpMessage.getName(), otpMessage.getOtpCode(), otpMessage.getEmail()
            );
            List<MailJetRecipient> mailJetRecipients = new ArrayList<>();
            mailJetRecipients.add(new MailJetRecipient(otpMessage.getEmail(),otpMessage.getName()));

            SendMailJetEMailCommand command = getSendMailJetEMailCommand(otpMessage.getName(),
                    otpMessage.getOtpCode(), mailJetRecipients);
            mediator.send(command);
            // Simulación de proceso de validación o envío de email
            System.out.println("📩 Mensaje OTP recibido y procesado:");
            System.out.println(otpText);

        } catch (Exception e) {
            System.err.println("❌ Error al procesar el mensaje OTP: " + e.getMessage());
        }
    }

        private static @NotNull SendMailJetEMailCommand getSendMailJetEMailCommand(String name,String code,
                                                                                   List<MailJetRecipient> mailJetRecipients) {
        List<MailJetVar> vars = Arrays.asList(
                new MailJetVar("username", name),
                new MailJetVar("otp_token", code)
        );

        int  templateId = MailjetTemplateEnum.OTP.getTemplateId();

        return new SendMailJetEMailCommand(mailJetRecipients, vars, new ArrayList<>(),
                "Código de verificación",String.valueOf(templateId));
    }
}