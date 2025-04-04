package com.kynsof.patients.infrastructure.services.rabbitMQ.patientUpdate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kynsof.patients.infrastructure.services.rabbitMQ.patientCreate.Person;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class UpdatePatientProducer {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    private static final String EXCHANGE_NAME = "paciente.update.exchange"; // Valor quemado

    public UpdatePatientProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendPersonEvent(Person person) {
        try {
            String message = objectMapper.writeValueAsString(person);
            rabbitTemplate.convertAndSend(EXCHANGE_NAME, "", message);
            System.out.println("📤 Evento enviado al exchange '" + EXCHANGE_NAME + "': " + message);
        } catch (JsonProcessingException e) {
            System.err.println("❌ Error al convertir el objeto Person a JSON: " + e.getMessage());
        }
    }
}