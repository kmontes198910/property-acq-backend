package com.kynsof.treatments.application.service.rabbitMQ;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    private final ObjectMapper objectMapper;

    public RabbitMQConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "paciente.treatment")
    public void receiveMessage(String message) {
        try {
            Person person = objectMapper.readValue(message, Person.class);
            System.err.println("📥 Evento recibido en TREATMENT: " + person);
        } catch (Exception e) {
            System.err.println("❌ Error procesando el mensaje en TREATMENT: " + e.getMessage());
        }
    }
}