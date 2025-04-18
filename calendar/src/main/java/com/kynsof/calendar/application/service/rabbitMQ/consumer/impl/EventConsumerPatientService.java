package com.kynsof.calendar.application.service.rabbitMQ.consumer.impl;

import com.kynsof.calendar.application.service.rabbitMQ.dto.RabbitMQPatientDto;
import com.kynsof.calendar.domain.dto.PatientDto;
import com.kynsof.calendar.domain.dto.enumType.PatientStatus;
import com.kynsof.calendar.domain.service.IPatientsService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EventConsumerPatientService {

    private final IPatientsService patientsService;

    public EventConsumerPatientService(IPatientsService patientsService) {
        this.patientsService = patientsService;
    }

    @RabbitListener(queues = "patient.queue.calendar")
    public void handleCompanyEvent(RabbitMQPatientDto event) {
        this.patientsService.create(new PatientDto(
                event.getId(), 
                event.getIdentification(), 
                event.getEmail(), 
                event.getName(), 
                event.getLastName(), 
                PatientStatus.valueOf(event.getStatus()), 
                event.getImage(), 
                event.getProfession()
        ));
    }
}
