package com.kynsof.hospitalizationService.infrastructure.service.rabbitMQ.consumer.impl;


import com.kynsof.hospitalizationService.domain.service.IPatientsService;
import com.kynsof.hospitalizationService.infrastructure.repositories.query.PatientsReadDataJPARepository;
import com.kynsof.hospitalizationService.infrastructure.service.rabbitMQ.consumer.dto.RabbitMQPatientDeleteDto;
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


    @RabbitListener(queues = "patient.delete.queue.hospitalization")
    public void handleCompanyEvent(RabbitMQPatientDeleteDto event) {
        boolean isEntity = query.findById(event.getId()).isPresent();
        if(isEntity){
            this.service.delete(event.getId());
        }

    }
}
