package com.kynsof.evaluation.infrastructure.service.rabbitMQ;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {

    private final ObjectMapper objectMapper;

    public RabbitMQConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "paciente.evaluation")
    public void receiveMessage(String message) {
        try {
            System.out.println("📨 Mensaje recibido en EVALUATION: " + message);

            Person person = objectMapper.readValue(message, Person.class);
            System.out.println("✅ Procesado correctamente en EVALUATION: " + person);
        } catch (Exception e) {
            System.err.println("❌ Error procesando el mensaje en EVALUATION: " + e.getMessage());
        }
    }
}