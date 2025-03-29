package com.kynsof.calendar.application.service.rabbitMQ;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQConsumer {


    private final ObjectMapper objectMapper;

    public RabbitMQConsumer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void receiveMessage(String message) {
        try {
            System.err.println(message);
            Person person = objectMapper.readValue(message, Person.class);
            System.err.println("📥 Evento recibido en CALENDAR: " + person);
        } catch (Exception e) {
            System.err.println("❌ Error procesando el mensaje en CALENDAR: " + e.getMessage());
        }
    }
}