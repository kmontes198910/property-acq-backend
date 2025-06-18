package com.kynsof.treatments.application.service.rabbitMQ.consumer.impl;


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
    private final PatientsReadDataJPARepository query;

    public EventConsumerPatientDeleteService(IPatientsService service, PatientsReadDataJPARepository query) {
        this.service = service;
        this.query = query;
    }


    @RabbitListener(queues = "patient.delete.queue.treatments")
    public void handleCompanyEvent(RabbitMQPatientDeleteDto event) {
        boolean isEntity = query.findById(event.getId()).isPresent();
        if(isEntity){
            this.service.delete(event.getId());
        }

    }
}
