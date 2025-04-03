package com.kynsof.patients.controller;


import com.kynsof.patients.infrastructure.services.rabbitMQ.Person;
import com.kynsof.patients.infrastructure.services.rabbitMQ.CreatePatientProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/patients-events")
public class PatientEventController {

    private final CreatePatientProducer publisher;

    public PatientEventController(CreatePatientProducer publisher) {
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