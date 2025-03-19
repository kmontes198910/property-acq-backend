package com.kynsof.patients.controller;


import com.kynsof.patients.infrastructure.services.rabbitMQ.Person;
import com.kynsof.patients.infrastructure.services.rabbitMQ.RabbitMQProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/patients-events")
public class PatientEventController {

    private final RabbitMQProducer publisher;

    public PatientEventController(RabbitMQProducer publisher) {
        this.publisher = publisher;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendPerson(@RequestBody Person person) {
        if (person.getId() == null || person.getId().isEmpty()) {
            person.setId(UUID.randomUUID().toString()); // Generar ID si no está presente
        }
        publisher.sendPersonEvent(person);
        return ResponseEntity.ok("✅ Persona enviada a RabbitMQ: " + person);
    }
}