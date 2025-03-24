package com.kynsoft.notification.infrastructure.service.rabbitMq.welcome;

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
public class WelcomeMessageConsumer {

    private final ObjectMapper objectMapper;
      private final IMediator mediator;

    public WelcomeMessageConsumer(ObjectMapper objectMapper, IMediator mediator) {
        this.objectMapper = objectMapper;
        this.mediator = mediator;
    }

    @RabbitListener(queues = RabbitMQConfig.WELCOME_QUEUE)
    public void receiveWelcomeMessage(String jsonMessage) {
        try {
            // Convertir JSON String a objeto WelcomeMessageDTO
            WelcomeMessageDTO message = objectMapper.readValue(jsonMessage, WelcomeMessageDTO.class);
            List<MailJetRecipient> mailJetRecipients = new ArrayList<>();
            mailJetRecipients.add(new MailJetRecipient(message.getEmail(), message.getFirstname()+ " " + message.getLastname()));
            SendMailJetEMailCommand command = getSendMailJetEMailCommand(mailJetRecipients, message.getEmail(),
                    message.getFirstname()+ " " + message.getLastname(),  message.getPassword());
            mediator.send(command);
            // Generar mensaje de bienvenida
            String welcomeText = String.format(
                    "🎉 ¡Bienvenido, %s %s! 🎉\n" +
                            "Enviamos la confirmación a tu correo: %s.",
                    message.getFirstname(), message.getLastname(), message.getEmail()
            );

            // Simular el procesamiento del mensaje
            System.out.println("📩 Mensaje recibido y procesado:");
            System.out.println(welcomeText);

        } catch (Exception e) {
            System.err.println("❌ Error al deserializar el mensaje: " + e.getMessage());
        }
    }

        private static @NotNull SendMailJetEMailCommand getSendMailJetEMailCommand( List<MailJetRecipient> mailJetRecipients,
                                                                                    String email, String fullName,
                                                                                    String password) {
        List<MailJetVar> vars = Arrays.asList(
                new MailJetVar("user_name", email),
                new MailJetVar("name", fullName),
                new MailJetVar("temp_password", password),
                new MailJetVar("temp_email", email)
        );

        int  templateId = MailjetTemplateEnum.WELCOM.getTemplateId();

        return new SendMailJetEMailCommand(mailJetRecipients, vars, new ArrayList<>(),
                "Correo de Bienvenida",String.valueOf(templateId));
    }
}