package com.kynsof.treatments.application.service.rabbitMQ.consumer.impl;


import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsof.treatments.application.service.rabbitMQ.Dto.RabbitMQPatientDeleteDto;
import com.kynsof.treatments.domain.service.IPatientsService;
import com.kynsof.treatments.infrastructure.repositories.query.PatientsReadDataJPARepository;
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

    @RabbitListener(queues = "patient.delete.queue.treatments")
    public void handleCompanyEvent(RabbitMQPatientDeleteDto event) {
        try {
            service.findById(event.getId());
            service.delete(event.getId());
        } catch (BusinessNotFoundException e) {
            log.warn(e.getMessage(), event.getId());
        }
    }
}