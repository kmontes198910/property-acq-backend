package com.kynsoft.cirugia.infrastructure.services.rabbitMQ.consumer.impl;


import com.kynsof.share.core.domain.exception.BusinessNotFoundException;
import com.kynsoft.cirugia.domain.service.IPatientsService;
import com.kynsoft.cirugia.infrastructure.repository.query.PatientsReadDataJPARepository;
import com.kynsoft.cirugia.infrastructure.services.rabbitMQ.dto.RabbitMQPatientDeleteDto;
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

    @RabbitListener(queues = "patient.delete.queue.cirugia")
    public void handleCompanyEvent(RabbitMQPatientDeleteDto event) {
        try {
            service.delete(event.getId());
        } catch (BusinessNotFoundException e) {
            log.warn("Paciente con ID {} no encontrado para eliminar en cirugía. Se ignora.", event.getId());
        }
    }
}
