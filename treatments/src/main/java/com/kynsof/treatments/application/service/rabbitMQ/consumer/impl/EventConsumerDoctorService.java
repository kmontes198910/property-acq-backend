package com.kynsof.treatments.application.service.rabbitMQ.consumer.impl;

import com.kynsof.treatments.application.service.rabbitMQ.Dto.DoctorRabbitMqDto;
import com.kynsof.treatments.domain.dto.DoctorDto;
import com.kynsof.treatments.domain.dto.enumDto.Status;
import com.kynsof.treatments.domain.service.IDoctorService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EventConsumerDoctorService {

    private final IDoctorService doctorService;

    public EventConsumerDoctorService(IDoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @RabbitListener(queues = "doctor.queue.treatments")
    public void handleCompanyEvent(DoctorRabbitMqDto event) {
        this.doctorService.create(new DoctorDto(
                event.getId(), 
                event.getIdentification(), 
                event.getName(), 
                event.getLastName(), 
                event.getRegisterNumber(), 
                event.getImage(), 
                Status.valueOf(event.getStatus())
        ));
    }
}
