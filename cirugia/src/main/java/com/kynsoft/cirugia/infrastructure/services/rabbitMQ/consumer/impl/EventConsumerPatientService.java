package com.kynsoft.cirugia.infrastructure.services.rabbitMQ.consumer.impl;

import com.kynsoft.cirugia.domain.dto.PatientDto;
import com.kynsoft.cirugia.domain.service.IPatientsService;
import com.kynsoft.cirugia.infrastructure.services.rabbitMQ.dto.RabbitMQPatientDto;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EventConsumerPatientService {

    private final IPatientsService patientsService;

    public EventConsumerPatientService(IPatientsService patientsService) {
        this.patientsService = patientsService;
    }

    @RabbitListener(queues = "patient.queue.cirugia")
    public void handleCompanyEvent(RabbitMQPatientDto event) {
        this.patientsService.create(new PatientDto(
                event.getId(), 
                event.getIdentification(), 
                event.getEmail(), 
                event.getName(), 
                event.getLastName(), 
                event.getImage(), 
                event.getProfession()
        ));
    }
}
