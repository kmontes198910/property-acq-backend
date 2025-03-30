package com.kynsof.hospitalizationService.infrastructure.service.rabbitMQ;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    private final ObjectMapper objectMapper;

    public RabbitMQConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "paciente.hospitalization")
    public void receiveMessage(String message) {
        try {
            Person person = objectMapper.readValue(message, Person.class);
            System.out.println("📥 Evento recibido en HOSPITALIZATION: " + person);
        } catch (Exception e) {
            System.err.println("❌ Error procesando el mensaje en HOSPITALIZATION: " + e.getMessage());
        }
    }
}