package com.kynsof.payment.application.service.rabbitMQ;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    private final ObjectMapper objectMapper;

    public RabbitMQConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "paciente.payment")
    public void receiveMessage(String message) {
        try {
            System.err.println("📨 Mensaje recibido PAYMENT: " + message);

            Person person = objectMapper.readValue(message, Person.class);
            System.err.println("✅ Procesado correctamente en PAYMENT: " + person);
        } catch (Exception e) {
            System.err.println("❌ Error procesando el mensaje en PAYMENT: " + e.getMessage());
        }
    }
}