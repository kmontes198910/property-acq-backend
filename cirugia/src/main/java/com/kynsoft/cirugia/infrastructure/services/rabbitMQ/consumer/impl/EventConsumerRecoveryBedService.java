package com.kynsoft.cirugia.infrastructure.services.rabbitMQ.consumer.impl;


import com.kynsoft.cirugia.domain.dto.RecoveryBed;
import com.kynsoft.cirugia.domain.service.IRecoveryBedService;

import com.kynsoft.cirugia.infrastructure.repository.query.RecoveryBedReadRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventConsumerRecoveryBedService {

    private final IRecoveryBedService recoveryBedService;

    public EventConsumerRecoveryBedService(IRecoveryBedService recoveryBedService) {
        this.recoveryBedService = recoveryBedService;
    }

    @RabbitListener(queues = "recovery-bed.queue.cirugia")
    public void handleCompanyEvent(RecoveryBed event) {
        try {
            recoveryBedService.findById(event.getId());
            recoveryBedService.update(event);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                recoveryBedService.create(event);
            } else {
                throw e;
            }
        }
    }
}