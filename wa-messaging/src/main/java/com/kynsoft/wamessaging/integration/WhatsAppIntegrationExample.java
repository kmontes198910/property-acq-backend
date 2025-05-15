package com.kynsoft.wamessaging.integration;

import com.kynsoft.wamessaging.application.dto.SendMessageRequest;
import com.kynsoft.wamessaging.domain.entity.MessageType;
import com.kynsoft.wamessaging.infrastructure.client.WhatsAppMessagingClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Ejemplo que muestra cómo los otros microservicios pueden integrarse con wa-messaging
 * Esta clase es solo un ejemplo y debe ser adaptada en cada servicio que requiera enviar mensajes
 */
@Service
public class WhatsAppIntegrationExample {

    private final WhatsAppMessagingClient messagingClient;

    @Autowired
    public WhatsAppIntegrationExample(WhatsAppMessagingClient messagingClient) {
        this.messagingClient = messagingClient;
    }

    /**
     * Ejemplo de envío de recordatorio de cita
     */
    public void sendAppointmentReminder(String patientPhone, String patientName, String date, String time, String doctor) {
        String message = String.format(
                "Hola %s, le recordamos su cita médica para el día %s a las %s con el Dr. %s. " +
                "Por favor, llegue 15 minutos antes. Si necesita reprogramar, contáctenos.",
                patientName, date, time, doctor);

        SendMessageRequest request = SendMessageRequest.builder()
                .recipientPhone(patientPhone)
                .recipientName(patientName)
                .messageContent(message)
                .messageType(MessageType.TEXT)
                .build();

        messagingClient.sendMessage(request);
    }

    /**
     * Ejemplo de envío de resultado de examen disponible
     */
    public void sendTestResultNotification(String patientPhone, String patientName, String testType, String resultUrl) {
        String message = String.format(
                "Hola %s, ya están disponibles los resultados de su %s. " +
                "Puede consultar los resultados en el siguiente enlace: %s",
                patientName, testType, resultUrl);

        SendMessageRequest request = SendMessageRequest.builder()
                .recipientPhone(patientPhone)
                .recipientName(patientName)
                .messageContent(message)
                .messageType(MessageType.TEXT)
                .build();

        messagingClient.sendMessage(request);
    }
    
    /**
     * Ejemplo de envío de imagen de receta médica
     */
    public void sendPrescriptionImage(String patientPhone, String patientName, String prescriptionUrl) {
        SendMessageRequest request = SendMessageRequest.builder()
                .recipientPhone(patientPhone)
                .recipientName(patientName)
                .messageContent("Su receta médica")
                .messageType(MessageType.IMAGE)
                .mediaUrl(prescriptionUrl)
                .build();

        messagingClient.sendMessage(request);
    }
}
