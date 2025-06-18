package com.kynsof.calendar.application.service.rabbitMQ.consumer.impl;


import com.kynsof.calendar.application.service.rabbitMQ.dto.RabbitMQPatientDeleteDto;
import com.kynsof.calendar.domain.service.IPatientsService;
import com.kynsof.calendar.infrastructure.repository.query.PatientsReadDataJPARepository;
import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EventConsumerPatientDeleteService {

    private final IPatientsService service;

    public EventConsumerPatientDeleteService(IPatientsService service) {
        this.service = service;
    }

    @RabbitListener(queues = "patient.delete.queue.calendar")
    public void handleCompanyEvent(RabbitMQPatientDeleteDto event) {
        try {
            service.delete(event.getId());
        } catch (BusinessNotFoundException e) {
            log.warn("Paciente con ID {} no encontrado para eliminar. Se ignora.", event.getId());
        }
    }
}