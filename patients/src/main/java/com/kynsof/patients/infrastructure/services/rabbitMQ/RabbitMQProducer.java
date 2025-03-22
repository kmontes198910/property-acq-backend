package com.kynsof.patients.infrastructure.services.rabbitMQ;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    public RabbitMQProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendPersonEvent(Person person) {
        try {
            String message = objectMapper.writeValueAsString(person);
            rabbitTemplate.convertAndSend(exchangeName, "", message);
            System.out.println("📤 Evento enviado al exchange '" + exchangeName + "': " + message);
        } catch (JsonProcessingException e) {
            System.err.println("❌ Error al convertir el objeto Person a JSON: " + e.getMessage());
        }
    }
}